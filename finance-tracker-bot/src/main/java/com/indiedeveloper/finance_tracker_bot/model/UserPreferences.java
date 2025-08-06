package com.indiedeveloper.finance_tracker_bot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@Table(name = "user_preferences")
@NoArgsConstructor
@AllArgsConstructor
public class UserPreferences {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String currency = "RUB";

    @Column(nullable = false, unique = true)
    private Long chatId;
}
