package com.firman.demo.crud.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BaseResponseDTO<T> {
    private String status;
    private String message;
    private Instant timestamp;
    private Object errors;
    private T data;

    public BaseResponseDTO(T data){
        this.status = "0";
        this.message = "Success";
        this.timestamp = Instant.now();
        this.data = data;
    }
}
