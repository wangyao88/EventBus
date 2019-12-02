package com.mohan.project.event.bus;

import lombok.Data;

/**
 * @author mohan
 */
@Data
public final class DefaultEventBus implements EventBus {

    private Dispatcher dispatcher;
    private Registry registry;

    public DefaultEventBus() {
        this.registry = new Registry();
        this.dispatcher = new Dispatcher(registry);
    }

    @Override
    public void register(Object listener) {
        registry.register(listener);
    }

    @Override
    public void register(Object ...listeners) {
        if(listeners != null) {
            for (Object listener : listeners) {
                registry.register(listener);
            }
        }
    }

    @Override
    public void unregister(Object listener) {
        registry.unregister(listener);
    }

    @Override
    public void unregister(Object ...listeners) {
        if(listeners != null) {
            for (Object listener : listeners) {
                registry.unregister(listener);
            }
        }
    }

    @Override
    public void publish(Object event) {
        dispatcher.publish(event);
    }

    @Override
    public void publish(String topic, Object event) {
        dispatcher.publish(topic, event);
    }

    @Override
    public void asyncPublish(Object event, Runnable runnable) {
        dispatcher.asyncPublish(event, runnable);
    }

    @Override
    public void asyncPublish(String topic, Object event, Runnable runnable) {
        dispatcher.asyncPublish(topic, event, runnable);
    }

    @Override
    public Statistic getStatistic() {
        return null;
    }
}
