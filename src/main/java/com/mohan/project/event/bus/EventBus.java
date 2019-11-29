package com.mohan.project.event.bus;

import java.util.function.Consumer;

/**
 * @author mohan
 */
public interface EventBus {

    void register(Object listener);

    void unregister(Object listener);

    void publish(Object event);

    void publish(String topic, Object event);

    void asyncPublish(Consumer consumer, Object event);

    void asyncPublish(Consumer consumer, String topic, Object event);

    Statistic getStatistic();
}
