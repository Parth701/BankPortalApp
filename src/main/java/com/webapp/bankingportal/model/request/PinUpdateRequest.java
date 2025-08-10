package com.webapp.bankingportal.model.request;

public record PinUpdateRequest(String accountNumber, String oldPin, String newPin, String password) {
}
