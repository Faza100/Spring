package com.indiedeveloper.finance_tracker_bot.service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.indiedeveloper.finance_tracker_bot.model.enums.Category;
import com.indiedeveloper.finance_tracker_bot.model.enums.Type;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class OperationStateService {

    private final Map<Long, OperationData> operationState = new ConcurrentHashMap<>();

    private static class OperationData {
        Long chatId;
        Type type;
        Category category;
        BigDecimal amountRUB;
        String description;
    }

    public void setType(Long chatId, Type type) {
        if (type != null) {
            getOperationData(chatId).type = type;
        }
    }

    public Type getType(Long chatId) {
        return getOperationData(chatId).type;

    }

    public void setCategory(Long chatId, Category category) {
        if (category != null) {
            getOperationData(chatId).category = category;
        }
    }

    public Category getCategory(Long chatId) {
        return getOperationData(chatId).category;
    }

    public void setAmountRUB(Long chatId, BigDecimal amountRUB) {
        if (amountRUB != null) {
            getOperationData(chatId).amountRUB = amountRUB;
        }
    }

    public BigDecimal getAmountRUB(Long chatId) {
        return getOperationData(chatId).amountRUB;
    }

    public void setDescription(Long chatId, String description) {
        getOperationData(chatId).description = description;
    }

    public String getDescription(Long chatId) {
        return getOperationData(chatId).description;
    }

    public void setChatId(Long chatId) {
        if (chatId != null) {
            getOperationData(chatId).chatId = chatId;
        }
    }

    public Long getChatId(Long chatId) {
        return getOperationData(chatId).chatId;
    }

    public OperationData getOperationData(Long chatId) {
        return operationState.computeIfAbsent(chatId, n -> new OperationData());
    }

    public void clearOperationData(Long chatId) {
        if (chatId != null) {
            operationState.remove(chatId);
        }
    }
}
