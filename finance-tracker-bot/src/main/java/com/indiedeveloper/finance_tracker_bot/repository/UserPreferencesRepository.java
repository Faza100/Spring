package com.indiedeveloper.finance_tracker_bot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.indiedeveloper.finance_tracker_bot.model.UserPreferences;

public interface UserPreferencesRepository extends JpaRepository<UserPreferences, Long> {
    Optional<UserPreferences> findByChatId(Long chatId);

    @Modifying
    @Query("UPDATE UserPreferences up SET up.currency = :currency WHERE up.chatId = :chatId")
    void updateCurrencyByChatId(@Param("chatId") Long chatId, @Param("currency") String currency);
}
