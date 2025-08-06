package com.indiedeveloper.finance_tracker_bot.service;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.indiedeveloper.finance_tracker_bot.utils.Keyboard;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@RequiredArgsConstructor
@Log4j
public class UserPreferencesService {

    private final FinanceService financeService;

    private static final Set<String> SUPPORTED_CURRENCIES = Set.of(
            "USD", "EUR", "BYN", "PLN", "CNY", "RUB");

    public SendMessage handleUpdate(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return null;
        }

        String messageText = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());

        try {
            if (messageText.equals("/start")) {
                return handleStartCommand(chatId);
            } else if (SUPPORTED_CURRENCIES.contains(messageText)) {
                return handleCurrencySelection(chatId, messageText);
            } else if (messageText.equals("Изменить валюту")) {
                return handleChangeCurrency(chatId);
            } else if (messageText.equals("Текущий счет")) {
                return handleCurrentBalance(chatId);
            }
        } catch (Exception e) {
            message.setText("Произошла ошибка. Пожалуйста, попробуйте позже");
            return message;
        }

        return null;
    }

    private SendMessage handleStartCommand(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText("Выберите валюту:");
        message.setReplyMarkup(Keyboard.getKeyboardStart(chatId));
        return message;
    }

    private SendMessage handleCurrencySelection(Long chatId, String currency) {
        try {
            financeService.updateUserCurrency(chatId, currency).join();
            SendMessage message = new SendMessage();
            message.setChatId(chatId.toString());
            message.setText("Валюта успешно изменена на " + currency +
                    "\n");
            message.setReplyMarkup(Keyboard.getKeyboardMenu(chatId));
            return message;
        } catch (Exception e) {
            return createErrorMessage(chatId, "Ошибка при изменении валюты");
        }
    }

    private SendMessage handleChangeCurrency(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText("Выберите валюту:");
        message.setReplyMarkup(Keyboard.getKeyboardStart(chatId));
        return message;
    }

    private SendMessage handleCurrentBalance(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());

        financeService.getFormattedBalance(chatId)
                .thenAccept(message::setText)
                .exceptionally(ex -> {
                    message.setText("Ошибка при получении баланса");
                    log.error("Error getting balance", ex);
                    return null;
                });

        return message;
    }

    private SendMessage createErrorMessage(Long chatId, String errorMessage) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(errorMessage);
        return message;
    }
}