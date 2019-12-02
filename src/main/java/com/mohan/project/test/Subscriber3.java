package com.mohan.project.test;

import com.mohan.project.event.bus.Subscribe;

/**
 * @author mohan
 */
public class Subscriber3 {

    @Subscribe
    public void sub1(String msg) {
        System.out.println("Subscriber3 sub1 " + msg);
    }

    @Subscribe
    public void sub2(String msg, int num) {
        System.out.println("Subscriber3 sub1 " + msg + num);
    }

    @Subscribe
    public void sub3(int num) {
        System.out.println("Subscriber3 sub2 " + num);
    }
}
