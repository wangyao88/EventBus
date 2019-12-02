package com.mohan.project.event.bus;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * @author mohan
 */
class Dispatcher {

    static final String DEFAULT_TOPIC = "default-topic";

    private Registry registry;

    Dispatcher(Registry registry) {
        this.registry = registry;
    }

    void publish(Object event) {
        if (event == null) {
            throw new EventException("event 为空");
        }
        doPublish(DEFAULT_TOPIC, event);
    }

    void publish(String topic, Object event) {
        doPublish(topic, event);
    }

    void asyncPublish(Object event, Runnable runnable) {
        doAsyncPublish(DEFAULT_TOPIC, event, runnable);
    }

    void asyncPublish(String topic, Object event, Runnable runnable) {
        doAsyncPublish(topic, event, runnable);
    }

    private void doAsyncPublish(String topic, Object event, Runnable runnable) {
        CompletableFuture.runAsync(() -> doPublish(topic, event))
                .whenComplete((Void, throwable) -> {
                    if (throwable != null) {
                        throwable.printStackTrace();
                    }else {
                        runnable.run();
                    }

                });
    }

    private Set<Subscriber> getSubscriber(String topic, Object event) {
        Set<Subscriber> subscribers = registry.getSubscriber(topic, event);
        if (subscribers == null) {
            subscribers = new HashSet<>();
        }
        return subscribers;
    }

    private void invoke(Set<Subscriber> subscribers, Object event) {
        for (Subscriber subscriber : subscribers) {
            subscriber.invoke(event);
        }
    }

    private void doPublish(String topic, Object event) {
        Set<Subscriber> subscriber = getSubscriber(topic, event);
        invoke(subscriber, event);
    }
}
