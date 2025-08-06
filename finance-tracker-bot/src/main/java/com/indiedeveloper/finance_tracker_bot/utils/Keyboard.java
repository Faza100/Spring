package com.indiedeveloper.finance_tracker_bot.utils;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import lombok.extern.log4j.Log4j;

@Log4j
public class Keyboard {

    public static ReplyKeyboard getKeyboardStart(Long chatId) {

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow rowUSDAndEUR = new KeyboardRow();
        rowUSDAndEUR.add("USD");
        rowUSDAndEUR.add("EUR");
        KeyboardRow rowBYNAndRUB = new KeyboardRow();
        rowBYNAndRUB.add("BYN");
        rowBYNAndRUB.add("RUB");
        KeyboardRow rowPLNAndCNY = new KeyboardRow();
        rowPLNAndCNY.add("PLN");
        rowPLNAndCNY.add("CNY");

        keyboardRows.add(rowUSDAndEUR);
        keyboardRows.add(rowBYNAndRUB);
        keyboardRows.add(rowPLNAndCNY);

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup(keyboardRows);
        markup.setResizeKeyboard(true);

        return markup;
    }

    public static ReplyKeyboard getKeyboardMenu(Long chatId) {

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow rowOperation = new KeyboardRow();
        rowOperation.add("Добавить операцию");
        KeyboardRow rowAccount = new KeyboardRow();
        rowAccount.add("Текущий счет");
        KeyboardRow rowStatistics = new KeyboardRow();
        rowStatistics.add("Статистика");
        KeyboardRow rowСurrency = new KeyboardRow();
        rowСurrency.add("Изменить валюту");

        keyboardRows.add(rowOperation);
        keyboardRows.add(rowAccount);
        keyboardRows.add(rowStatistics);
        keyboardRows.add(rowСurrency);

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup(keyboardRows);
        markup.setResizeKeyboard(true);

        return markup;

    }

    public static ReplyKeyboard getKeyboardCategoryExpense(Long chatId) {

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add("Продукты");
        row1.add("Кафе");
        row1.add("Развлечения");
        KeyboardRow row2 = new KeyboardRow();
        row2.add("Одежда");
        row2.add("Транспорт");
        row2.add("Семья");
        KeyboardRow row3 = new KeyboardRow();
        row2.add("Здоровье");
        row2.add("Животные");
        row2.add("Назад");

        keyboardRows.add(row1);
        keyboardRows.add(row2);
        keyboardRows.add(row3);

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup(keyboardRows);
        markup.setResizeKeyboard(true);

        return markup;
    }

    public static ReplyKeyboard getKeyboardCategoryIncome(Long chatId) {

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add("Зарплата");
        row1.add("Акции");
        KeyboardRow row2 = new KeyboardRow();
        row2.add("Бизнес");
        row2.add("Пенсия");
        KeyboardRow row3 = new KeyboardRow();
        row3.add("Другое");
        row3.add("Назад");

        keyboardRows.add(row1);
        keyboardRows.add(row2);
        keyboardRows.add(row3);

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup(keyboardRows);
        markup.setResizeKeyboard(true);

        return markup;
    }

    public static ReplyKeyboard getKeyboardDescription(Long chatId) {

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add("Пропустить");

        keyboardRows.add(row1);

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup(keyboardRows);
        markup.setResizeKeyboard(true);

        return markup;
    }

    public static ReplyKeyboard getKeyboardType(Long chatId) {

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow rowExpenses = new KeyboardRow();
        rowExpenses.add("Расходы");
        KeyboardRow rowIncome = new KeyboardRow();
        rowIncome.add("Доходы");
        KeyboardRow rowИack = new KeyboardRow();
        rowИack.add("Назад");

        keyboardRows.add(rowExpenses);
        keyboardRows.add(rowIncome);
        keyboardRows.add(rowИack);

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup(keyboardRows);
        markup.setResizeKeyboard(true);

        return markup;
    }

    public static ReplyKeyboardRemove keyboardRemove(Long chatId) {

        ReplyKeyboardRemove keyboardRemove = new ReplyKeyboardRemove();
        keyboardRemove.setRemoveKeyboard(true);
        keyboardRemove.setSelective(false);

        return keyboardRemove;
    }
}
