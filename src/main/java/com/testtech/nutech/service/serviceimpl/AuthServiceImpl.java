package com.testtech.nutech.service.serviceimpl;

/*
Created By IntelliJ IDEA 2022.1.3 (Community Edition)
Build #IC-221.5921.22, built on June 21, 2022
@Author JEJE a.k.a Jefri S
Java Developer
Created On 10/2/2023 15:00
@Last Modified 10/2/2023 15:00
Version 1.0
*/

import com.testtech.nutech.entity.Customer;
import com.testtech.nutech.entity.UserCredential;
import com.testtech.nutech.entity.UserDetailImpl;
import com.testtech.nutech.handler.ResponeHandler;
import com.testtech.nutech.model.request.AuthRequest;
import com.testtech.nutech.model.request.LoginRequest;
import com.testtech.nutech.model.response.LoginResponse;
import com.testtech.nutech.repository.UserCredentialRepository;
import com.testtech.nutech.security.BCryptUtils;
import com.testtech.nutech.security.JwtUtils;
import com.testtech.nutech.service.AuthService;
import com.testtech.nutech.service.AuthValidation;
import com.testtech.nutech.service.CustomerService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Log4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final AuthValidationServiceImpl validateAuthRequest;

    private final UserCredentialRepository userCredentialRepository;

    private final AuthenticationManager authenticationManager;

    private final BCryptUtils bCryptUtils;

    private final CustomerService customerService;

    private final JwtUtils jwtUtils;



    @Transactional(rollbackOn = Exception.class)
    @Override
    public ResponseEntity<Object> register(AuthRequest authRequest) {
            // === Validasi manual langsung di sini ===

            if (authRequest.getEmail() == null || authRequest.getEmail().trim().isEmpty()) {
                return new ResponseEntity<>(
                        new ResponeHandler<>(101, "Email tidak boleh kosong", null),
                        HttpStatus.BAD_REQUEST
                );
            }

            if (!authRequest.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
                return new ResponseEntity<>(
                        new ResponeHandler<>(102, "Format email tidak valid", authRequest.getEmail()),
                        HttpStatus.BAD_REQUEST
                );
            }

            if (userCredentialRepository.existsByEmail(authRequest.getEmail())) {
                return new ResponseEntity<>(
                        new ResponeHandler<>(103, "Email sudah terdaftar", authRequest.getEmail()),
                        HttpStatus.CONFLICT
                );
            }

            if (authRequest.getPassword() == null || authRequest.getPassword().length() < 8) {
                return new ResponseEntity<>(
                        new ResponeHandler<>(104, "Password minimal 8 karakter", null),
                        HttpStatus.BAD_REQUEST
                );
            }

            if (authRequest.getFirst_name() == null || authRequest.getFirst_name().trim().isEmpty()) {
                return new ResponseEntity<>(
                        new ResponeHandler<>(105, "Nama depan tidak boleh kosong", null),
                        HttpStatus.BAD_REQUEST
                );
            }

            if (authRequest.getLast_name() == null || authRequest.getLast_name().trim().isEmpty()) {
                return new ResponseEntity<>(
                        new ResponeHandler<>(106, "Nama belakang tidak boleh kosong", null),
                        HttpStatus.BAD_REQUEST
                );
            }

            // === Proses simpan user jika valid ===

            String hashedPassword = bCryptUtils.hashPassword(authRequest.getPassword());

            UserCredential userCredential = UserCredential.builder()
                    .email(authRequest.getEmail())
                    .password(hashedPassword)
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);

            Customer customer = Customer.builder()
                    .first_name(authRequest.getFirst_name())
                    .last_name(authRequest.getLast_name())
                    .email(authRequest.getEmail())
                    .userCredential(userCredential)
                    .build();
            customerService.create(customer);

            ResponeHandler<Object> response = new ResponeHandler<>(0, "Berhasil daftar silahkan login", authRequest.getEmail());
            return new ResponseEntity<>(response, HttpStatus.OK);

    }


    @Override
    public ResponseEntity<Object> login(LoginRequest loginRequest, HttpServletResponse responseHttp) {

        try {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailImpl userDetail = (UserDetailImpl) authentication.getPrincipal();

        String token = jwtUtils.generateToken(userDetail.getEmail());

        // start cookie
        //Cookie cookie = new Cookie("jwt", token);
        ResponseCookie cookie = ResponseCookie.from("jwt", token)
                .httpOnly(false)
                .secure(false)
                .path("/")
                .maxAge(60 * 60 * 24 * 30)
                .sameSite("Lax")
                .build();
        responseHttp.setHeader(HttpHeaders.SET_COOKIE, String.valueOf(cookie));
        // end cookie

            System.out.println("token = " + token);
            System.out.println("set cookie = " + cookie);

            LoginResponse loginResponse = LoginResponse.builder()
                .token(token)
                .build();

        ResponeHandler<Object> response = new ResponeHandler<>(0, "Sukses Login", loginResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);

        }
        catch (BadCredentialsException e) {
            // Jika username atau password salah
            ResponeHandler<Object> response = new ResponeHandler<>(103, "Username atau Password salah", null);
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

        } catch (UsernameNotFoundException e) {
            // Jika username tidak ditemukan di database
            ResponeHandler<Object> response = new ResponeHandler<>(104, "Username tidak ditemukan", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            // Jika terjadi error lain
            ResponeHandler<Object> response = new ResponeHandler<>(500, "Terjadi kesalahan pada server", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<Object> logout(HttpServletResponse response) {
        // Clear security context
        SecurityContextHolder.clearContext();

        // Clear JWT cookie by setting empty value and 0 max age
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(false)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, String.valueOf(cookie));

        ResponeHandler<Object> responseHandler = new ResponeHandler<>(0, "Berhasil logout", null);
        return new ResponseEntity<>(responseHandler, HttpStatus.OK);
    }

}
