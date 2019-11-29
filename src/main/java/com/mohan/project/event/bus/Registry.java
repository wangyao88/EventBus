package com.mohan.project.event.bus;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

/**
 * @author mohan
 */
class Registry {

    private final CopyOnWriteArraySet<Class<?>> registryTypes = new CopyOnWriteArraySet<>();
    private final Map<String, CopyOnWriteArraySet<Subscriber>> subscriberContainer = new ConcurrentHashMap<>();

    synchronized void register(Object listener) {
        Class<?> aClass = listener.getClass();
        if(registryTypes.contains(aClass)) {
            return;
        }
        Map<String, List<Subscriber>> subscriberMap = findSubscribers(listener);
        subscriberMap.forEach((topic, subscribers) -> {
            subscriberContainer.computeIfAbsent(topic, key -> new CopyOnWriteArraySet<>());
            subscriberContainer.get(topic).addAll(subscribers);
        });
        registryTypes.add(aClass);
    }

    synchronized void unregister(Object listener) {
        Class<?> aClass = listener.getClass();
        if(!registryTypes.contains(aClass)) {
            return;
        }
        Map<String, List<Subscriber>> subscriberMap = findSubscribers(listener);
        subscriberMap.forEach((topic, subscribers) -> {
            CopyOnWriteArraySet<Subscriber> subscriberCopyOnWriteArraySet = subscriberContainer.get(topic);
            if(subscriberCopyOnWriteArraySet != null) {
                subscriberCopyOnWriteArraySet.removeAll(subscribers);
                if(subscriberCopyOnWriteArraySet.isEmpty()) {
                    subscriberContainer.remove(topic);
                }
            }
        });
        registryTypes.remove(aClass);
    }

    private Map<String, List<Subscriber>> findSubscribers(Object listener) {
        Class<?> aClass = listener.getClass();
        Method[] declaredMethods = aClass.getDeclaredMethods();
        List<Subscriber> subscriberList = new ArrayList<>();
        for (Method declaredMethod : declaredMethods) {
            Subscribe[] annotationsByType = declaredMethod.getAnnotationsByType(Subscribe.class);
            if(annotationsByType != null) {
                for (Subscribe subscribe : annotationsByType) {
                    String topic = subscribe.topic();
                    subscriberList.add(new Subscriber(topic, listener, declaredMethod));
                }
            }
        }
        return subscriberList.stream().collect(Collectors.groupingBy(Subscriber::getTopic));
    }
}
