package com.mohan.project.event.bus;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

/**
 * @author mohan
 */
class Registry {

    private final CopyOnWriteArraySet<Class<?>> registryTypes = new CopyOnWriteArraySet<>();
    private final Map<String, Map<Class<?>, CopyOnWriteArraySet<Subscriber>>> subscriberContainer = new ConcurrentHashMap<>();

    synchronized void register(Object listener) {
        if(listener == null) {
            return;
        }
        Class<?> aClass = listener.getClass();
        if(registryTypes.contains(aClass)) {
            return;
        }
        Map<String, Map<Class<?>, List<Subscriber>>> topicMap = findSubscribers(listener);
        topicMap.forEach((topic, classMap) -> {
            subscriberContainer.computeIfAbsent(topic, key -> new ConcurrentHashMap<>());
            Map<Class<?>, CopyOnWriteArraySet<Subscriber>> classCopyOnWriteArraySetMap = subscriberContainer.get(topic);
            classMap.forEach((clazz, subscribers) -> {
                classCopyOnWriteArraySetMap.computeIfAbsent(clazz, key -> new CopyOnWriteArraySet<>());
                classCopyOnWriteArraySetMap.get(clazz).addAll(subscribers);
            });
        });
        registryTypes.add(aClass);
    }

    synchronized void unregister(Object listener) {
        if(listener == null) {
            return;
        }
        Class<?> aClass = listener.getClass();
        if(!registryTypes.contains(aClass)) {
            return;
        }
        Map<String, Map<Class<?>, List<Subscriber>>> topicMap = findSubscribers(listener);
        topicMap.forEach((topic, classMap) -> {
            Map<Class<?>, CopyOnWriteArraySet<Subscriber>> classCopyOnWriteArraySetMap = subscriberContainer.get(topic);
            if(classCopyOnWriteArraySetMap != null) {
                classMap.forEach((clazz, needUnregisterSubscribers) -> {
                    CopyOnWriteArraySet<Subscriber> subscribers = classCopyOnWriteArraySetMap.get(clazz);
                    if(subscribers != null) {
                        subscribers.removeAll(needUnregisterSubscribers);
                        if(subscribers.isEmpty()) {
                            classCopyOnWriteArraySetMap.remove(clazz);
                        }
                    }
                });
                if(classCopyOnWriteArraySetMap.isEmpty()) {
                    subscriberContainer.remove(topic);
                }
            }
        });
        registryTypes.remove(aClass);
    }

    synchronized Set<Subscriber> getSubscriber(String topic, Object event) {
        Map<Class<?>, CopyOnWriteArraySet<Subscriber>> classCopyOnWriteArraySetMap = subscriberContainer.get(topic);
        if(classCopyOnWriteArraySetMap != null) {
            Class<?> convertClass = ClassTypeConvertor.convert(event.getClass());
            return classCopyOnWriteArraySetMap.get(convertClass);
        }
        return new HashSet<>();
    }

    private Map<String, Map<Class<?>, List<Subscriber>>> findSubscribers(Object listener) {
        Class<?> aClass = listener.getClass();
        Method[] declaredMethods = aClass.getDeclaredMethods();
        List<Subscriber> subscriberList = new ArrayList<>();
        for (Method declaredMethod : declaredMethods) {
            if(declaredMethod.getParameterCount() > 1) {
                continue;
            }
            Subscribe[] subscribeAnnotations = declaredMethod.getAnnotationsByType(Subscribe.class);
            if(subscribeAnnotations != null) {
                Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
                for (Subscribe subscribe : subscribeAnnotations) {
                    String topic = subscribe.topic();
                    subscriberList.add(new Subscriber(topic, listener, declaredMethod, parameterTypes[0]));
                }
            }
        }
        Map<String, Map<Class<?>, List<Subscriber>>> subscriberContainerTemp = new ConcurrentHashMap<>();
        Map<String, List<Subscriber>> topicMap = subscriberList.stream().collect(Collectors.groupingBy(Subscriber::getTopic));
        topicMap.forEach((topic, subscribers) -> {
            Map<Class<?>, List<Subscriber>> classMap = subscribers.stream().collect(Collectors.groupingBy(Subscriber::getParamType));
            subscriberContainerTemp.put(topic, classMap);
        });
        return subscriberContainerTemp;
    }
}
