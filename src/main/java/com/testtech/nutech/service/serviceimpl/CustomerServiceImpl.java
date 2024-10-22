package com.testtech.nutech.service.serviceimpl;

/*
Created By IntelliJ IDEA 2022.1.3 (Community Edition)
Build #IC-221.5921.22, built on June 21, 2022
@Author JEJE a.k.a Jefri S
Java Developer
Created On 9/22/2023 15:29
@Last Modified 9/22/2023 15:29
Version 1.0
*/

import com.testtech.nutech.utils.ImageStorage;
import com.testtech.nutech.entity.Customer;
import com.testtech.nutech.handler.ResponeHandler;
import com.testtech.nutech.model.request.CustomerUpdateRequest;
import com.testtech.nutech.model.response.CustomerResponse;
import com.testtech.nutech.repository.CustomerRepository;
import com.testtech.nutech.security.JwtUtils;
import com.testtech.nutech.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final ImageStorage imageStorage;

    private final JwtUtils jwtUtils;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Customer create(Customer request) {
                Customer customer = Customer.builder()
                        .idCustomer(request.getIdCustomer())
                        .first_name(request.getFirst_name())
                        .last_name(request.getLast_name())
                        .email(request.getEmail())
                        .userCredential(request.getUserCredential())
                        .build();
                 customerRepository.saveAndFlush(customer);


        return Customer.builder()
                .idCustomer(customer.getIdCustomer())
                .first_name(customer.getFirst_name())
                .last_name(customer.getLast_name())
                .email(customer.getEmail())
                .userCredential(customer.getUserCredential())
                .build();
    }

    @Override
    public Customer getById(String id) {
        return null;
    }

    @Override
    public ResponseEntity<Object> getByEmail(String token) {

        String email = jwtUtils.getEmailByToken(token);

        Optional<Customer> customerRepositoryByEmail = customerRepository.findByEmail(email);

        if (customerRepositoryByEmail.isPresent()) {

            Customer getCustomer = customerRepositoryByEmail.get();

            CustomerResponse getProfile = CustomerResponse.builder()
                    .email(email)
                    .first_name(getCustomer.getFirst_name())
                    .last_name(getCustomer.getLast_name())
                    .profile_image(getCustomer.getProfile_image())
                    .build();

            ResponeHandler<Object> response = new ResponeHandler<>(0, "Sukses",getProfile);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        // Jika user tidak ditemukan
        ResponeHandler<Object> response = new ResponeHandler<>(404, "User tidak ditemukan", null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }

    @Override
    public ResponeHandler<Object> getBalance(String token) {

        String email = jwtUtils.getEmailByToken(token);

            Optional<Customer> customer = customerRepository.findByEmail(email);



            if (customer.isPresent()) {
                Integer balance = customer.get().getBalance(); // Ambil saldo dari user

                // Buat map untuk data respons
                Map<String, Object> responseData = new HashMap<>();
                responseData.put("balance", balance);

                // Kembalikan ResponeHandler dengan status 0 (sukses) dan balance
                return new ResponeHandler<>(0, "Get Balance Berhasil", responseData);
            }
            return null;
    }

    @Override
    public ResponseEntity<Object> uploadImage(String token, MultipartFile file) {

        String email = jwtUtils.getEmailByToken(token);

        // Validasi tipe file (hanya jpeg dan png yang diizinkan)
        if (!file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/png")) {
            ResponeHandler<Object> response = new ResponeHandler<>(102, "Format gambar tidak sesuai", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Mencari user berdasarkan email
        Optional<Customer> customerOpt = customerRepository.findByEmail(email);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();

            try {
                String profileImageUrl = imageStorage.uploadImageToStorage(file);

                // Update profile image
                customer.setProfile_image(profileImageUrl);
                customerRepository.save(customer);

                // Siapkan respons sukses
                CustomerResponse userProfile = CustomerResponse.builder()
                        .email(email)
                        .first_name(customer.getFirst_name())
                        .last_name(customer.getLast_name())
                        .profile_image(profileImageUrl)
                        .build();

                ResponeHandler<Object> response = new ResponeHandler<>(0, "Update Profile Image berhasil", userProfile);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (IOException e) {
                // Handling jika terjadi kesalahan saat menyimpan gambar
                ResponeHandler<Object> response = new ResponeHandler<>(500, "Gagal mengupload gambar", null);
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return null;

    }


    @Override
    public List<Customer> getAll(String mesage, HttpStatus httpStatus) {
        return customerRepository.findAll();
    }


    @Transactional(rollbackOn = Exception.class)
    @Override
    public ResponseEntity<Object> update(String token, CustomerUpdateRequest updateRequest) {
        // Mendapatkan email dari token JWT
        String email = jwtUtils.getEmailByToken(token);

        // Mencari user berdasarkan email
        Optional<Customer> customerOpt = customerRepository.findByEmail(email);

        // Jika user ditemukan
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();

            // Update field profile dengan data baru dari request
            if (updateRequest.getFirst_name() != null) {
                customer.setFirst_name(updateRequest.getFirst_name());
            }
            if (updateRequest.getLast_name() != null) {
                customer.setLast_name(updateRequest.getLast_name());
            }

            // Simpan perubahan ke database
            customerRepository.saveAndFlush(customer);

            // Siapkan response yang diinginkan
            CustomerResponse userProfile = CustomerResponse.builder()
                    .email(email)
                    .first_name(customer.getFirst_name())
                    .last_name(customer.getLast_name())
                    .profile_image(customer.getProfile_image())
                    .build();

            ResponeHandler<Object> response = new ResponeHandler<>(0, "Update Profile berhasil", userProfile);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return null;
    }
    @Override
    public Customer deleteById(String id) {
        customerRepository.deleteById(id);
        return null;
    }




}
