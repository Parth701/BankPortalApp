package com.webapp.bankingportal.service;

import java.util.List;

import com.webapp.bankingportal.model.response.TransactionResponse;

public interface TransactionService {

	List<TransactionResponse> getAllTransactionsByAccountNumber(String accountNumber);

}
