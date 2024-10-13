package com.testtech.nutech.model.response;

/*
Created By IntelliJ IDEA 2022.1.3 (Community Edition)
Build #IC-221.5921.22, built on June 21, 2022
@Author JEJE a.k.a Jefri S
Java Developer
Created On 10/2/2023 13:11
@Last Modified 10/2/2023 13:11
Version 1.0
*/

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RegisterResponse<T> {

    private HttpStatus httpStatus;
    private Integer statusCode;
    private String message;
    private T data;

}
