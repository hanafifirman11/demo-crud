package com.firman.demo.crud.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public enum ResponseEnum {
    SUCCESS("0", "Success", Instant.now(), "success"),
    INVALID_PARAMETER("400", "The request for %s is invalid, because %s.", Instant.now(), "invalid_parameter_request"),
    INTERNAL_SERVER_ERROR("500", "Internal Server Error, please contact the administrator, error id : %s.", Instant.now(), "internal_server_error"),
    USER_NOT_FOUND("400", "User not found", Instant.now(), "invalid_parameter_request");

    private final String status;
    private final String message;
    private final Instant timestamp;
    private final String type;
}
