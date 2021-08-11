package com.firman.demo.crud.handler;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.firman.demo.crud.common.MapperHelper;
import com.firman.demo.crud.dto.BaseResponseDTO;
import com.firman.demo.crud.enums.ResponseEnum;
import com.firman.demo.crud.exception.BusinessException;
import com.firman.demo.crud.exception.InternalServerErrorException;
import org.springframework.core.codec.DecodingException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

/**
 * ErrorHandler
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @SuppressWarnings("rawtypes")
    public BaseResponseDTO methodArgumentNotValidException(MethodArgumentNotValidException e) {
        ResponseEnum responseEnum = ResponseEnum.INVALID_PARAMETER;
        return BaseResponseDTO.builder()
            .errors(constructErrors(responseEnum, e))
            .status(ResponseEnum.INVALID_PARAMETER.getStatus())
            .message("Invalid Parameter Request")
            .timestamp(Instant.now())
            .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DecodingException.class)
    @SuppressWarnings("rawtypes")
    public BaseResponseDTO decodingException(DecodingException e) {
        return BaseResponseDTO.builder()
                .status("400")
                .message(e.getMessage())
                .timestamp(Instant.now())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    @SuppressWarnings("rawtypes")
    public BaseResponseDTO channelNotAllowException(BusinessException e) {
        return BaseResponseDTO.builder()
                .status(e.getError().getStatus())
                .message(e.getMessage())
                .timestamp(Instant.now())
                .build();
    }

    private List<String> constructErrors(ResponseEnum responseEnum, MethodArgumentNotValidException e) {
        return e.getFieldErrors()
            .stream()
            .collect(Collectors.groupingBy(FieldError::getField))
            .entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue()
                .stream()
                .map(fieldError -> String.format(responseEnum.getMessage(), MapperHelper.camelToSnake(entry.getKey()), fieldError.getDefaultMessage()))
                .collect(Collectors.toList())))
            .entrySet()
            .stream()
            .flatMap(stringListEntry -> stringListEntry.getValue().stream())
            .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalServerErrorException.class)
    @SuppressWarnings("rawtypes")
    public BaseResponseDTO internalServerErrorException(InternalServerErrorException e) {
        return BaseResponseDTO.builder()
                .status(e.getError().getStatus())
                .message(e.getMessage())
                .timestamp(Instant.now())
                .build();
    }

}
