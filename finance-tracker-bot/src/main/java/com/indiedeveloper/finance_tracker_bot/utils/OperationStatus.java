package com.indiedeveloper.finance_tracker_bot.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@RequiredArgsConstructor
@Component
@Log4j
public class OperationStatus {

    private final Map<Long, String> userStates = new HashMap<>();

    public String getState(Long chatId) {
        return userStates.getOrDefault(chatId, "EMPTY");
    }

    public void setState(Long chatId, String state) {
        userStates.put(chatId, state);
    }

}
