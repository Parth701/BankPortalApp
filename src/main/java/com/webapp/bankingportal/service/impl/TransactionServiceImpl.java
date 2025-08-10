package com.webapp.bankingportal.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import com.webapp.bankingportal.service.TransactionService;
import org.springframework.stereotype.Service;
import com.webapp.bankingportal.model.response.TransactionResponse;
import com.webapp.bankingportal.mapper.TransactionConverter;
import com.webapp.bankingportal.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;
import lombok.val;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionConverter transactionConverter;

    @Override
    public List<TransactionResponse> getAllTransactionsByAccountNumber(String accountNumber) {
        val transactions = transactionRepository
                .findBySourceAccount_AccountNumberOrTargetAccount_AccountNumber(accountNumber, accountNumber);

        val transactionDTOs = transactions.parallelStream()
                .map(transactionConverter::toDto)
                .sorted((t1, t2) -> t2.getTransactionDate().compareTo(t1.getTransactionDate()))
                .collect(Collectors.toList());

        return transactionDTOs;
    }

}
