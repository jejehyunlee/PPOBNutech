package com.testtech.nutech.utils;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@Component
public class ImageStorage {

    @Value("${local.storage.path}")
    private String localStoragePath; // Path untuk penyimpanan lokal

    public String uploadImageToStorage(MultipartFile file) throws IOException {
        // Membuat direktori jika belum ada
        File directory = new File(localStoragePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Menghasilkan nama unik untuk file
        String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // Path lengkap untuk file yang akan disimpan
        Path filePath = Paths.get(localStoragePath, uniqueFileName);

        // Menyimpan file
        Files.copy(file.getInputStream(), filePath);

        // Mengembalikan URL lokal untuk file
        return "http://localhost:8080/uploads/" + uniqueFileName; // Sesuaikan dengan port dan path yang digunakan
    }

}
