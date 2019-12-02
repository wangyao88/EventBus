package com.mohan.project.test;

import com.mohan.project.event.bus.Subscribe;

/**
 * @author mohan
 */
public class Subscriber1 {

    @Subscribe(topic = "topic1")
    public void sub1(String msg) {
        System.out.println("Subscriber1 sub1 " + msg);
    }

    @Subscribe(topic = "topic1")
    public void sub2(int num) {
        System.out.println("Subscriber1 sub2 " + num);
    }
}
