package com.webapp.bankingportal.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.bankingportal.model.request.LoginRequest;
import com.webapp.bankingportal.model.request.OtpRequest;
import com.webapp.bankingportal.model.request.OtpVerificationRequest;
import com.webapp.bankingportal.entity.User;
import com.webapp.bankingportal.exception.InvalidTokenException;
import com.webapp.bankingportal.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        log.info("Register request received for username={}, email={}", user.getName(), user.getEmail());

        ResponseEntity<String> response = userService.registerUser(user);

        log.info("User registered successfully: username={}", user.getName());
        return response;    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request)
            throws InvalidTokenException {
        log.info("Login attempt for username={}", loginRequest.identifier());
        ResponseEntity<String> response = userService.login(loginRequest, request);
        log.info("Login successful for username={}", loginRequest.identifier());
        return response;
    }

    @PostMapping("/generate-otp")
    public ResponseEntity<String> generateOtp(@RequestBody OtpRequest otpRequest) {

        log.info("OTP generation request for username={}", otpRequest.identifier());

        ResponseEntity<String> response = userService.generateOtp(otpRequest);

        log.info("OTP generated successfully for username={}", otpRequest.identifier());
        return response;    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtpAndLogin(@RequestBody OtpVerificationRequest otpVerificationRequest)
            throws InvalidTokenException {
        log.info("OTP verification attempt for username={}", otpVerificationRequest.identifier());

        ResponseEntity<String> response = userService.verifyOtpAndLogin(otpVerificationRequest);

        log.info("OTP verification successful for username={}", otpVerificationRequest.identifier());
        return response;
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        log.info("Update request for username={}", user.getName());

        ResponseEntity<String> response = userService.updateUser(user);

        log.info("User details updated successfully: username={}", user.getName());
        return response;    }

    @GetMapping("/logout")
    public ModelAndView logout(@RequestHeader("Authorization") String token)
            throws InvalidTokenException {
        log.info("Logout request received");

        ModelAndView result = userService.logout(token);

        log.info("Logout successful");
        return result;
    }

}
