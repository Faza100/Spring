package com.indiedeveloper.finance_tracker_bot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.indiedeveloper.finance_tracker_bot.service.OperationService;
import com.indiedeveloper.finance_tracker_bot.service.UserPreferencesService;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequiredArgsConstructor
@Log4j
public class BotController extends TelegramLongPollingBot {
    @Value("${bot.token}")
    private String botToken;

    private final UserPreferencesService userPreferencesService;
    private final OperationService operationService;

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotUsername() {
        return "SSFinanceTrackerBot";
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage response = null;

        response = operationService.addOperation(update);
        if (response != null) {
            try {
                execute(response);
            } catch (TelegramApiException e) {
                log.error("Ошибка отправки сообщения", e);
            }
        }
        response = userPreferencesService.handleUpdate(update);
        if (response != null) {
            try {
                execute(response);
            } catch (TelegramApiException e) {
                log.error("Ошибка отправки сообщения", e);
            }
        }
    }
}
