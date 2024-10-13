package com.testtech.nutech.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class BannerResponse {

    private String bannerName;
    private String bannerImage;
    private String description;

}
