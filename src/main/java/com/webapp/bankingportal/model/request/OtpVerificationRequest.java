package com.webapp.bankingportal.model.request;

public record OtpVerificationRequest(String identifier, String otp) {
}
