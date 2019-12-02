package com.mohan.project.test;

import com.mohan.project.event.bus.DefaultEventBus;
import com.mohan.project.event.bus.EventBus;

/**
 * @author mohan
 */
public class TestMain {

    public static void main(String[] args) {
        EventBus eventBus = new DefaultEventBus();
        eventBus.register(new Subscriber1(), new Subscriber2(), new Subscriber3());
        eventBus.asyncPublish("topic1", "event bus", () -> {
            System.out.println("----------");
        });
        eventBus.publish(111);
    }
}

