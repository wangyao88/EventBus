package com.mohan.project.event.bus;

import lombok.AllArgsConstructor;
import lombok.Data;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscriber that = (Subscriber) o;
        return Objects.equals(topic, that.topic) &&
                Objects.equals(target, that.target) &&
                Objects.equals(method, that.method);
    }

    @Override
    public int hashCode() {

        return Objects.hash(topic, target, method);
    }
}
