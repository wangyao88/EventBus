package com.mohan.project.event.bus;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author mohan
 */
@Data
@AllArgsConstructor
class Subscriber {

    private String topic;
    private Object target;
    private Method method;
    private Class<?> paramType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscriber that = (Subscriber) o;
        return Objects.equals(topic, that.topic) &&
                Objects.equals(target, that.target) &&
                Objects.equals(method, that.method) &&
                Objects.equals(paramType, that.paramType);
    }

    @Override
    public int hashCode() {

        return Objects.hash(topic, target, method, paramType);
    }

    void invoke(Object event) {
        try {
            this.method.invoke(target, event);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new EventException(e);
        }
    }
}
