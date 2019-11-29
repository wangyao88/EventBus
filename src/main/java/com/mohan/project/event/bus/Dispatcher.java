package com.mohan.project.event.bus;

import java.util.function.Consumer;

/**
 * @author mohan
 */
class Dispatcher {

    private Registry registry;

    Dispatcher(Registry registry) {
        this.registry = registry;
    }

    void publish(Object event) {

    }

    void publish(String topic, Object event) {

    }

    void asyncPublish(Consumer consumer, Object event) {

    }

    void asyncPublish(Consumer consumer, String topic, Object event) {

    }
}
