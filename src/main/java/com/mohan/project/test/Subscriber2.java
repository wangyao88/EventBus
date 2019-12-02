package com.mohan.project.test;

import com.mohan.project.event.bus.Subscribe;

/**
 * @author mohan
 */
public class Subscriber2 {

    @Subscribe(topic = "topic1")
    public void sub1(String msg, int num) {
        System.out.println("Subscriber2 sub1 " + msg + num);
    }

    @Subscribe
    public void sub2(int num) {
        System.out.println("Subscriber2 sub2 " + num);
    }
}
