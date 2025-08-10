package com.webapp.bankingportal.model.request;

public record ResetPasswordRequest(String identifier, String resetToken, String newPassword) {
}
