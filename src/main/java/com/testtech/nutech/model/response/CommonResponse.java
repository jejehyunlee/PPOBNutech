package com.testtech.nutech.model.response;

/*
Created By IntelliJ IDEA 2022.1.3 (Community Edition)
Build #IC-221.5921.22, built on June 21, 2022
@Author JEJE a.k.a Jefri S
Java Developer
Created On 9/22/2023 09:17
@Last Modified 9/22/2023 09:17
Version 1.0
*/

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CommonResponse<T> {

    private HttpStatus httpStatus;
    private Integer statusCode;
    private String message;
    private T data;
}
