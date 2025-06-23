package com.testtech.nutech.service.serviceimpl;

import com.testtech.nutech.entity.Customer;
import com.testtech.nutech.entity.UserCredential;
import com.testtech.nutech.entity.UserDetailImpl;
import com.testtech.nutech.handler.ResponeHandler;
import com.testtech.nutech.model.request.LoginRequest;
import com.testtech.nutech.model.response.LoginResponse;
import com.testtech.nutech.repository.UserCredentialRepository;
import com.testtech.nutech.security.BCryptUtils;
import com.testtech.nutech.security.JwtUtils;
import com.testtech.nutech.service.AuthValidation;
import com.testtech.nutech.model.request.AuthRequest;
import com.testtech.nutech.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthServiceImpl authService;
    @Mock
    private UserCredentialRepository userCredentialRepository;
    @Mock
    private BCryptUtils bCryptUtils;
    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private CustomerService customerService;

    @Test
    public void testRegisterSuccess() {



        // Arrange
        AuthRequest request = new AuthRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setFirst_name("John");
        request.setLast_name("Doe");

        when(bCryptUtils.hashPassword("password123")).thenReturn("hashed_password");

        UserCredential userCredential = UserCredential.builder()
                .email("test@example.com")
                .password("hashed_password")
                .build();

        Customer customer = Customer.builder()
                .email("test@example.com")
                .first_name("John")
                .last_name("Doe")
                .build();

        when(userCredentialRepository.saveAndFlush(any(UserCredential.class)))
                .thenReturn(userCredential);

        when(customerService.create(any(Customer.class)))
                .thenReturn(customer);

        // Act
        ResponseEntity<Object> response = authService.register(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ResponeHandler<?> body = (ResponeHandler<?>) response.getBody();
        assertNotNull(body);
        assertEquals(0, body.getStatus());
        assertEquals("Berhasil register silahkan login", body.getMessage());
        assertEquals("test@example.com", body.getData());

        System.out.println("Berhasil Register" +
                "\n✅ Email: " + request.getEmail() +
                "\n✅ Password: " + request.getPassword() +
                "\n✅ First Name: " + request.getFirst_name() +
                "\n✅ Last Name: " + request.getLast_name());
    }


    @Test
    void testRegisterFailed_EmptyEmail() {
        AuthRequest request = new AuthRequest();
        request.setEmail("www"); // Email invalid
        request.setPassword("password123");
        request.setFirst_name("Jefri");
        request.setLast_name("Saputra");

        // ACTUAL VALIDATOR, no mocking!
        ResponseEntity<Object> response = authService.register(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ResponeHandler<?> body = (ResponeHandler<?>) response.getBody();
        assert body != null;
        assertEquals(102, body.getStatus());
        assertEquals("Format email tidak valid", body.getMessage());
//
        System.out.println("Gagal Register" +
                "\n❌ Email: " + request.getEmail() +
                "\n❌ Password: " + request.getPassword() +
                "\n❌ First Name: " + request.getFirst_name() +
                "\n❌ Last Name: " + request.getLast_name());
    }

    @Test
    void testRegisterFailedInvalidPassword() {
        AuthRequest request = new AuthRequest();
        request.setEmail("test@example.com");
        request.setPassword("123"); // Password too short
        request.setFirst_name("John");
        request.setLast_name("Doe");

        ResponseEntity<Object> response = authService.register(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ResponeHandler<?> body = (ResponeHandler<?>) response.getBody();
        assertNotNull(body);
        assertEquals(104, body.getStatus());
        assertEquals("Password minimal 8 karakter", body.getMessage());
        assertNull(body.getData());

        System.out.println(body.getMessage() +
                "\n❌ Email: " + request.getEmail() +
                "\n❌ Password anda " + request.getPassword() + " kurang dari 8 karakter" +
                "\n❌ First Name: " + request.getFirst_name() +
                "\n❌ Last Name: " + request.getLast_name());
    }

    @Test
    void testLoginSuccess() {
        // Arrange
        LoginRequest request = new LoginRequest();
        request.setUsername("test@example.com");
        request.setPassword("password123");

        UserDetailImpl userDetail = UserDetailImpl.builder()
                .email("test@example.com")
                .build();

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetail);
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);

        when(jwtUtils.generateToken("test@example.com")).thenReturn("token123");

        MockHttpServletResponse responseHttp = new MockHttpServletResponse();
        // Act
        ResponseEntity<Object> response = authService.login(request, responseHttp);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Object body = response.getBody();
        assertNotNull(body);

        LoginResponse loginResponse = (LoginResponse) ((ResponeHandler<?>) body).getData();

        ResponeHandler<?> responeHandler = (ResponeHandler<?>) body;
        assertEquals(0, responeHandler.getStatus());
        assertEquals("Sukses Login", responeHandler.getMessage());
        assertEquals("token123", loginResponse.getToken());

        System.out.println("Sukses Login" +
                "\n✅ Email: " + userDetail.getEmail() +
                "\n✅ Token: " + loginResponse.getToken() +
                "\n✅ Set-Cookie: " + responseHttp.getHeader(HttpHeaders.SET_COOKIE));
    }

    @Test
    void testLoginFailedInvalidCredentials() {
        // Arrange
        LoginRequest request = new LoginRequest();
        request.setUsername("test@example.com");
        request.setPassword("wrong_password");


        // Mock Authentication
        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        // Mock response
        MockHttpServletResponse responseHttp = new MockHttpServletResponse();

        // Act
        ResponseEntity<Object> response = authService.login(request, responseHttp);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

        ResponeHandler<?> body = (ResponeHandler<?>) response.getBody();
        assertNotNull(body);
        assertEquals(103, body.getStatus()); // kode gagal login dari app-mu
        assertEquals("Username atau Password salah", body.getMessage());
        assertNull(body.getData());

        System.out.println("Gagal Login" +
                "\n❌ Email: " + request.getUsername() +
                "\n❌ Pesan: " + body.getMessage());

    }


    @Test
    void testLoginFailedUserNotFound() {
        // Arrange
        LoginRequest request = new LoginRequest();
        request.setUsername("nonexistent@example.com");
        request.setPassword("password123");

        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenThrow(new UsernameNotFoundException("User not found"));

        MockHttpServletResponse responseHttp = new MockHttpServletResponse();

        ResponseEntity<Object> response = authService.login(request, responseHttp);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        ResponeHandler<?> body = (ResponeHandler<?>) response.getBody();
        assertNotNull(body);
        assertEquals(104, body.getStatus());
        assertEquals("Username tidak ditemukan", body.getMessage());
        assertNull(body.getData());

        System.out.println("Username Tidak Ditemukan" +
                "\n❌ Email: " + request.getUsername() +
                "\n❌ Pesan: " + body.getMessage());
    }

    @Test
    void testRegisterFailedEmailExists() {
        // Arrange
        AuthRequest request = new AuthRequest();
        request.setEmail("existing@example.com");
        request.setPassword("password123");
        request.setFirst_name("John");
        request.setLast_name("Doe");

        when(userCredentialRepository.existsByEmail("existing@example.com")).thenReturn(true);

        // Act
        ResponseEntity<Object> response = authService.register(request);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        ResponeHandler<?> body = (ResponeHandler<?>) response.getBody();
        assertNotNull(body);
        assertEquals(103, body.getStatus());
        assertEquals("Email sudah terdaftar", body.getMessage());
        assertEquals("existing@example.com", body.getData());

        System.out.println("Gagal Register - Email Sudah Terdaftar" +
                "\n❌ Email: " + request.getEmail() +
                "\n❌ Pesan: " + body.getMessage());
    }

}