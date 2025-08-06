package com.indiedeveloper.finance_tracker_bot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.indiedeveloper.finance_tracker_bot.controller.BotController;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import okhttp3.OkHttpClient;

@Component
@RequiredArgsConstructor
@Log4j
public class BotConfig {

    private static final int STOP_ATTEMPTS = 5;
    private static final int STOP_DELAY_MS = 1000;

    private BotSession botSession;

    @Bean
    public OkHttpClient okHtptpClient() {
        return new OkHttpClient();
    }

    @Bean
    public TelegramBotsApi initializeBot(BotController botController) throws TelegramApiException {

        stopExistingSession();

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        this.botSession = telegramBotsApi.registerBot(botController);

        log.info("Бот успешно запущен. Session: " + botSession);
        return telegramBotsApi;
    }

    public void stopExistingSession() {
        if (botSession != null && botSession.isRunning()) {
            log.warn("Обнаружена активная сессия бота. Принудительная остоновка");
            botSession.stop();

            int attempts = 0;

            while (botSession.isRunning() && attempts < STOP_ATTEMPTS
                    && !Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(STOP_DELAY_MS);
                    attempts++;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.error("Поток был прерван во время остановки бота");
                    break;
                }
            }
            if (botSession.isRunning()) {
                log.error("Не удалось остановить предыдущую сессию бота");
            } else {
                log.info("Предыдущая сессия бота успешно остановлена");
            }
        }
    }

    @PreDestroy
    public void shutdown() {
        stopExistingSession();
    }

}