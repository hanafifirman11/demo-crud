package com.firman.demo.crud.exception;

import com.firman.demo.crud.enums.ResponseEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException {
    private final ResponseEnum error;
    private final String message;

    public BusinessException(ResponseEnum error, String message){
        super(message);
        this.error = error;
        this.message = message;
    }

    public BusinessException(ResponseEnum error) {
        this(error, error.getMessage());
    }
}
