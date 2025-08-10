package com.webapp.bankingportal.service;

import org.springframework.http.ResponseEntity;

import com.webapp.bankingportal.model.request.OtpRequest;
import com.webapp.bankingportal.model.request.OtpVerificationRequest;
import com.webapp.bankingportal.model.request.ResetPasswordRequest;
import com.webapp.bankingportal.entity.User;

public interface AuthService {
    public String generatePasswordResetToken(User user);

    public boolean verifyPasswordResetToken(String token, User user);

    public void deletePasswordResetToken(String token);

    public ResponseEntity<String> sendOtpForPasswordReset(OtpRequest otpRequest);

    public ResponseEntity<String> verifyOtpAndIssueResetToken(OtpVerificationRequest otpVerificationRequest);

    public ResponseEntity<String> resetPassword(ResetPasswordRequest resetPasswordRequest);

}
