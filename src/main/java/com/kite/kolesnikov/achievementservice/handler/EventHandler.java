package com.kite.kolesnikov.achievementservice.handler;

import org.springframework.stereotype.Component;

@Component
public interface EventHandler<T> {

    void handle(T event);
}
