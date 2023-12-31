package com.kite.kolesnikov.achievementservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kite.kolesnikov.achievementservice.exception.JsonDeserializationException;
import com.kite.kolesnikov.achievementservice.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractEventListener<T> implements MessageListener {
    private final ObjectMapper objectMapper;
    protected final List<EventHandler<T>> handlers;

    protected T deserializeJsonToEvent(Message message, Class<T> type) {
        T event;

        try {
            event = objectMapper.readValue(message.getBody(), type);
        } catch (IOException e) {
            log.error("Failed to deserialize Json {}: {}", message.getBody(), e.getMessage());
            throw new JsonDeserializationException(e);
        }

        return event;
    }
}
