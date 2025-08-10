package com.webapp.bankingportal.model.request;

public record AmountRequest(String accountNumber, String pin, double amount) {
}
