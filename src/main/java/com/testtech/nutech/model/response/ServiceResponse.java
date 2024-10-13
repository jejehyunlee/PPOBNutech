package com.testtech.nutech.model.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ServiceResponse {
    private String serviceCode;
    private String serviceName;
    private String serviceIcon;
    private Integer serviceTariff;
}
