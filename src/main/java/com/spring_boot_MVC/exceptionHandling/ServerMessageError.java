package com.spring_boot_MVC.exceptionHandling;

import java.time.LocalDateTime;

public record ServerMessageError(

        String message,

        String detailMessage,

        LocalDateTime errorTime

) {
}
