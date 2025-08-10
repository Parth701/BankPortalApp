package com.webapp.bankingportal.model.request;

public record FundTransferRequest(String sourceAccountNumber, String targetAccountNumber, double amount, String pin) {
}
