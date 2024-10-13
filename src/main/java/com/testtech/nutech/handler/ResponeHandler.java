package com.testtech.nutech.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;


@Data
@Builder(toBuilder = true)
@JsonPropertyOrder({"status", "message", "data"})  // Tentukan urutan field
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponeHandler<T> {
    private int status;
    private String message;
    private T data;

        public ResponeHandler(int status, String message, T data) {
            this.status = status;
            this.message = message;
            this.data = data;
        }

}
