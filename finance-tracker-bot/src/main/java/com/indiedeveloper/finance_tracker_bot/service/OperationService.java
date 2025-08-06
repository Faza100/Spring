package com.indiedeveloper.finance_tracker_bot.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.indiedeveloper.finance_tracker_bot.model.enums.Category;
import com.indiedeveloper.finance_tracker_bot.model.enums.Type;
import com.indiedeveloper.finance_tracker_bot.utils.Keyboard;
import com.indiedeveloper.finance_tracker_bot.utils.OperationStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@RequiredArgsConstructor
@Log4j
public class OperationService {

    private final FinanceService financeService;
    private final OperationStatus operationStatus;
    private final OperationStateService operationState;

    public SendMessage addOperation(Update update) {

        String messageText = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();

        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());

        if (messageText.equals("Добавить операцию") &&
                "EMPTY".equals(operationStatus.getState(chatId))) {
            message.setText("Выберите тип операции:");
            message.setReplyMarkup(Keyboard.getKeyboardType(chatId));
            operationState.setChatId(chatId);
            operationStatus.setState(chatId, "WAITING_FOR_TYPE");
            return message;

        } else if ("WAITING_FOR_TYPE".equals(operationStatus.getState(chatId))) {
            if (messageText.equals("Доходы")) {
                Type type = Type.INCOME;
                message.setText("Введите категорию доходов:");
                message.setReplyMarkup(Keyboard.getKeyboardCategoryIncome(chatId));
                operationState.setType(chatId, type);
                operationStatus.setState(chatId, "WAITING_FOR_CATEGORY_INCOME");
                return message;
            } else if (messageText.equals("Расходы")) {
                Type type = Type.EXPENSE;
                message.setText("Введите категорию расходов:");
                message.setReplyMarkup(Keyboard.getKeyboardCategoryExpense(chatId));
                operationState.setType(chatId, type);
                operationStatus.setState(chatId, "WAITING_FOR_CATEGORY_EXPENSE");
                return message;
            } else {
                message.setText("Выберите тип операции из кнопок");
                return message;
            }

        } else if ("WAITING_FOR_CATEGORY_INCOME".equals(operationStatus.getState(chatId))) {
            Category category = categoryIncome(messageText);
            if (category == null) {
                message.setText("Не верная кактегория доходов.Выберите тип операции из кнопок");
                return message;
            }
            message.setText("Введите сумму:");
            message.setReplyMarkup(Keyboard.keyboardRemove(chatId));
            operationState.setCategory(chatId, category);
            operationStatus.setState(chatId, "WAITING_FOR_AMOUNT");
            return message;

        } else if ("WAITING_FOR_CATEGORY_EXPENSE".equals(operationStatus.getState(chatId))) {
            Category category = categoryExpense(messageText);
            if (category == null) {
                message.setText("Не верная кактегория расходов.Выберите тип операции из кнопок");
                return message;
            }
            message.setText("Введите сумму:");
            message.setReplyMarkup(Keyboard.keyboardRemove(chatId));
            operationState.setCategory(chatId, category);
            operationStatus.setState(chatId, "WAITING_FOR_AMOUNT");
            return message;

        } else if ("WAITING_FOR_AMOUNT".equals(operationStatus.getState(chatId))) {
            if (isNumeric(messageText)) {
                BigDecimal amountRUB = new BigDecimal(messageText);
                message.setText("Описание:");
                message.setReplyMarkup(Keyboard.getKeyboardDescription(chatId));
                operationState.setAmountRUB(chatId, amountRUB);
                message.setReplyMarkup(Keyboard.getKeyboardDescription(chatId));
                operationStatus.setState(chatId, "WAITING_FOR_DESCRIPTION");
                return message;
            } else {
                message.setText("Введите корректную сумму (число больше 0)");
                return message;
            }

        } else if ("WAITING_FOR_DESCRIPTION".equals(operationStatus.getState(chatId))) {
            if (messageText.equals("Пропустить")) {
                operationState.setDescription(chatId, null);
            } else {
                String description = messageText;
                operationState.setDescription(chatId, description);
            }

            message.setReplyMarkup(Keyboard.keyboardRemove(chatId));

            Long finalChatId = operationState.getChatId(chatId);
            Type finalType = operationState.getType(chatId);
            Category finalCategory = operationState.getCategory(chatId);
            BigDecimal finalAmountRUB = operationState.getAmountRUB(chatId);
            String finalDiscription = operationState.getDescription(chatId);

            financeService.addOperation(finalChatId, finalType, finalCategory,
                    finalAmountRUB, finalDiscription);

            message.setText("Операция успешна добавлена");
            message.setReplyMarkup(Keyboard.getKeyboardMenu(chatId));

            return message;
        }
        return null;
    }

    private Category categoryExpense(String messageText) {
        if (messageText.equalsIgnoreCase("Продукты")) {
            return Category.PRODUCT;
        } else if (messageText.equalsIgnoreCase("Кафе")) {
            return Category.CAFE;
        } else if (messageText.equalsIgnoreCase("Развлечения")) {
            return Category.ENTERTAINMENT;
        } else if (messageText.equalsIgnoreCase("Транспорт")) {
            return Category.TRANSPORT;
        } else if (messageText.equalsIgnoreCase("Семья")) {
            return Category.FAMILY;
        } else if (messageText.equalsIgnoreCase("Здоровье")) {
            return Category.HEALTH;
        } else if (messageText.equalsIgnoreCase("Животные")) {
            return Category.ANIMALS;
        }
        return null;
    }

    private Category categoryIncome(String messageText) {
        if (messageText.equalsIgnoreCase("Зарплата")) {
            return Category.SALARY;
        } else if (messageText.equalsIgnoreCase("Пенсия")) {
            return Category.PENSION;
        } else if (messageText.equalsIgnoreCase("Акции")) {
            return Category.Shares;
        } else if (messageText.equalsIgnoreCase("Бизнес")) {
            return Category.BUSINESS;
        } else if (messageText.equalsIgnoreCase("Другое")) {
            return Category.OTHER;
        }

        return null;
    }

    private boolean isNumeric(String messageText) {
        if (messageText == null || messageText.trim().isEmpty()) {
            return false;
        }

        try {
            String normalized = messageText.trim().replace(',', '.');
            BigDecimal amount = new BigDecimal(normalized);

            return amount.compareTo(BigDecimal.ZERO) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
