package com.kite.kolesnikov.achievementservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kite.kolesnikov.achievementservice.dto.RecommendationEventDto;
import com.kite.kolesnikov.achievementservice.handler.EventHandler;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecommendationEventListener extends AbstractEventListener<RecommendationEventDto> {

    @Autowired
    public RecommendationEventListener(ObjectMapper objectMapper,
                                       List<EventHandler<RecommendationEventDto>> eventHandlers) {
        super(objectMapper, eventHandlers);
    }

    @Override
    public void onMessage(@NotNull Message message, byte[] pattern) {
        RecommendationEventDto recommendationEventDto = deserializeJsonToEvent(message, RecommendationEventDto.class);
        handlers.forEach(handler -> handler.handle(recommendationEventDto));
    }
}
