package com.indiedeveloper.finance_tracker_bot.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.indiedeveloper.finance_tracker_bot.model.UserOperation;

public interface UserOperationRepository extends JpaRepository<UserOperation, Long> {

    List<UserOperation> findByChatId(Long chatId);

    @Query("SELECT SUM(CASE WHEN op.type = 'INCOME' THEN op.amountRUB ELSE -op.amountRUB END) " +
            "FROM UserOperation op WHERE op.chatId = :chatId")
    BigDecimal calculateBalance(@Param("chatId") Long chatId);
}
