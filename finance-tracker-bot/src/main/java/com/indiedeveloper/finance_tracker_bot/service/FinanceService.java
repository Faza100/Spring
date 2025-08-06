package com.indiedeveloper.finance_tracker_bot.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.indiedeveloper.finance_tracker_bot.model.UserOperation;
import com.indiedeveloper.finance_tracker_bot.model.UserPreferences;
import com.indiedeveloper.finance_tracker_bot.model.enums.Category;
import com.indiedeveloper.finance_tracker_bot.model.enums.Type;
import com.indiedeveloper.finance_tracker_bot.repository.UserOperationRepository;
import com.indiedeveloper.finance_tracker_bot.repository.UserPreferencesRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@RequiredArgsConstructor
@Log4j
public class FinanceService {

    private final UserOperationRepository userOperationRepository;
    private final UserPreferencesRepository userPreferencesRepository;
    private final OperationStateService operationStateService;
    private final CurrencyService currencyService;

    @Async
    public UserOperation addOperation(Long chatId, Type type, Category category,
            BigDecimal amount, String description) {

        try {

            if (chatId == null || type == null || category == null || amount == null) {
                throw new IllegalArgumentException("Обязательные параметры не могут быть нулевыми");
            }

            String userCurrency = userPreferencesRepository.findByChatId(chatId)
                    .map(UserPreferences::getCurrency)
                    .orElse("RUB");

            BigDecimal amountRUB = userCurrency.equals("RUB")
                    ? amount
                    : currencyService.convertCurrency(amount, userCurrency, "RUB");
            if (amountRUB == null) {
                throw new IllegalStateException("Конвертация валюты вернула ноль");
            }

            UserOperation operation = UserOperation.builder()
                    .chatId(chatId)
                    .type(type)
                    .amountRUB(amountRUB)
                    .category(category)
                    .dateAdd(LocalDate.now())
                    .build();

            operation.setDescription(description);

            UserOperation savedOperation = userOperationRepository.save(operation);
            return savedOperation;
        } catch (Exception e) {
            throw e;
        } finally {
            operationStateService.clearOperationData(chatId);
        }
    }

    @Async
    public CompletableFuture<String> getFormattedBalance(Long chatId) {
        BigDecimal balanceRUB = userOperationRepository.calculateBalance(chatId);
        if (balanceRUB == null)
            balanceRUB = BigDecimal.ZERO;

        String userCurrency = userPreferencesRepository.findByChatId(chatId)
                .map(UserPreferences::getCurrency)
                .orElse("RUB");

        BigDecimal convertedBalance = currencyService.convertCurrency(balanceRUB,
                "RUB", userCurrency);

        String result = String.format("Баланс: %.2f %s (%.2f RUB)",
                convertedBalance, userCurrency, balanceRUB);

        return CompletableFuture.completedFuture(result);
    }

    @Async
    public CompletableFuture<Void> updateUserCurrency(Long chatId, String currency) {
        userPreferencesRepository.findByChatId(chatId).ifPresentOrElse(
                prefs -> {
                    prefs.setCurrency(currency);
                    userPreferencesRepository.save(prefs);
                },
                () -> {
                    UserPreferences newPrefs = UserPreferences.builder()
                            .chatId(chatId)
                            .currency(currency)
                            .build();
                    userPreferencesRepository.save(newPrefs);
                });

        return CompletableFuture.completedFuture(null);
    }

    @Async
    public CompletableFuture<List<UserOperation>> getUserOperations(Long chatId) {
        List<UserOperation> operations = userOperationRepository.findByChatId(chatId);
        return CompletableFuture.completedFuture(operations);
    }

    @Async
    public CompletableFuture<BigDecimal> calculateUserBalance(Long chatId) {
        BigDecimal balance = userOperationRepository.calculateBalance(chatId);
        return CompletableFuture.completedFuture(balance != null ? balance : BigDecimal.ZERO);
    }

}
