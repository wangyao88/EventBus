package com.mohan.project.event.bus;

/**
 * @author mohan
 */
public class EventException extends RuntimeException {

    public EventException(String msg) {
        super(msg);
    }

    public EventException(Throwable throwable) {
        super(throwable);
    }
}
