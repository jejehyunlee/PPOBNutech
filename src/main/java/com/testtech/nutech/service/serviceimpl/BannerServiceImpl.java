package com.testtech.nutech.service.serviceimpl;

import com.testtech.nutech.entity.Banner;
import com.testtech.nutech.entity.Services;
import com.testtech.nutech.handler.ResponeHandler;
import com.testtech.nutech.model.response.BannerResponse;
import com.testtech.nutech.model.response.ServiceResponse;
import com.testtech.nutech.repository.BannerRepository;
import com.testtech.nutech.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl {


    private final BannerRepository bannerRepository;

    private final ServiceRepository serviceRepository;

    public ResponseEntity<Object> getAllBanners() {

        List<Banner> banners = bannerRepository.findAll(); // Ambil semua banner dari database

        List<BannerResponse> bannerResponses = convertToBannerResponse(banners);

        // Jika data tidak kosong, kirim response dengan status sukses
        if (!banners.isEmpty()) {
            ResponeHandler<List<BannerResponse>> response = new ResponeHandler<>(0, "Sukses", bannerResponses);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return null;
    }
    public ResponseEntity<Object> getAllServices() {
        List<Services> services = serviceRepository.findAll(); // Ambil semua banner dari database

        List<ServiceResponse> serviceResponses = convertToServiceResponse(services);

        // Jika data tidak kosong, kirim response dengan status sukses
        if (!services.isEmpty()) {
            ResponeHandler<List<ServiceResponse>> response = new ResponeHandler<>(0, "Sukses", serviceResponses);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return null;
    }

    public List<BannerResponse> convertToBannerResponse(List<Banner> services) {
        return services.stream()
                .map(service -> new BannerResponse(
                        service.getBannerName(),
                        service.getBannerImage(),
                        service.getDescription()))
                .collect(Collectors.toList());
    }

    public List<ServiceResponse> convertToServiceResponse(List<Services> services) {


        return services.stream()
                .map(service -> {
                    ServiceResponse serviceDTO = new ServiceResponse(
                            service.getServiceCode(),
                            service.getServiceName(),
                            service.getBanner() != null ? service.getBanner().getBannerImage() : null,
                            service.getServiceTariff()
                    );
                    return serviceDTO;
                })
                .collect(Collectors.toList());
    }


}
