package com.pipikonda.rentbot.error;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BasicLogicException extends RuntimeException {

    private Enum<?> errorCode;
    private String message;

    public BasicLogicException(Enum<?> errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
