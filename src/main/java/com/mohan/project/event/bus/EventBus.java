package com.mohan.project.event.bus;

/**
 * @author mohan
 */
public interface EventBus {

    void register(Object listener);

    void register(Object ...listener);

    void unregister(Object listener);

    void unregister(Object ...listener);

    void publish(Object event);

    void publish(String topic, Object event);

    void asyncPublish(Object event, Runnable runnable);

    void asyncPublish(String topic, Object event, Runnable runnable);

    Statistic getStatistic();
}
