package com.webapp.bankingportal.model.request;

public record PinRequest(String accountNumber, String pin, String password) {
}
