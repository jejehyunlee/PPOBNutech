package com.testtech.nutech.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BannerRequest {

    private String bannerName;
    private String bannerImage;
    private String description;

}
