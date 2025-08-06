package com.indiedeveloper.finance_tracker_bot.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.indiedeveloper.finance_tracker_bot.model.enums.Category;
import com.indiedeveloper.finance_tracker_bot.model.enums.Type;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "user_operations")
@NoArgsConstructor
@AllArgsConstructor
public class UserOperation {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long chatId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(nullable = false)
    private BigDecimal amountRUB;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    private String description;

    @Column(nullable = false)
    private LocalDate dateAdd;
}
