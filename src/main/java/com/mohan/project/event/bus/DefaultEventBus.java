package com.mohan.project.event.bus;

import lombok.Builder;
import lombok.Data;

import java.util.function.Consumer;

/**
 * @author mohan
 */
@Data
@Builder
public final class DefaultEventBus implements EventBus {

    private Dispatcher dispatcher;
    private Registry registry;

    private DefaultEventBus() {
        this.registry = new Registry();
        this.dispatcher = new Dispatcher(registry);
    }

    @Override
    public void register(Object listener) {
        registry.register(listener);
    }

    @Override
    public void unregister(Object listener) {
        registry.unregister(listener);
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
    public void asyncPublish(Consumer consumer, Object event) {
        dispatcher.asyncPublish(consumer, event);
    }

    @Override
    public void asyncPublish(Consumer consumer, String topic, Object event) {
        dispatcher.asyncPublish(consumer, topic, event);
    }

    @Override
    public Statistic getStatistic() {
        return null;
    }
}
