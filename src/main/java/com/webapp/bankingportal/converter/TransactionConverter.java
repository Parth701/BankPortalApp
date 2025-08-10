package com.webapp.bankingportal.converter;

import org.springframework.stereotype.Component;

import com.webapp.bankingportal.model.response.TransactionResponse;
import com.webapp.bankingportal.entity.Transaction;

@Component
public class TransactionConverter {

    public TransactionResponse toDto(Transaction transaction) {
        return new TransactionResponse(transaction);
    }

}
