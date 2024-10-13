package com.testtech.nutech.controller;

import com.testtech.nutech.service.serviceimpl.BannerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/ppob/api/")
public class BannerServicesController {


    private final BannerServiceImpl bannerService;

    @GetMapping("banner")
    public ResponseEntity<Object> getAllBanners() {

        return bannerService.getAllBanners();
    }
    @GetMapping("service")
    public ResponseEntity<Object> getAllServices() {
       return bannerService.getAllServices();
    }


}
