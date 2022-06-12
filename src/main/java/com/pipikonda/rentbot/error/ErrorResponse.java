package com.pipikonda.rentbot.error;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ErrorResponse {

    Enum<?> errorCode;
    String errorText;
}
