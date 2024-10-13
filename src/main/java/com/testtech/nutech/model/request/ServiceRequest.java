package com.testtech.nutech.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ServiceRequest {

    private String serviceCode;
    private String serviceName;
    private String serviceIcon;
    private Integer serviceTariff;
}
