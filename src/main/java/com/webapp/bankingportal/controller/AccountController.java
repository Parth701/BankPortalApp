package com.webapp.bankingportal.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.webapp.bankingportal.model.request.AmountRequest;
import com.webapp.bankingportal.model.request.FundTransferRequest;
import com.webapp.bankingportal.model.request.PinRequest;
import com.webapp.bankingportal.model.request.PinUpdateRequest;
import com.webapp.bankingportal.service.AccountService;
import com.webapp.bankingportal.service.TransactionService;
import com.webapp.bankingportal.constants.ApiMessages;
import com.webapp.bankingportal.util.JsonUtil;
import com.webapp.bankingportal.util.LoggedinUserUtil;

import lombok.RequiredArgsConstructor;
import lombok.val;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;
    private final TransactionService transactionService;

    @GetMapping("/pin/check")
    public ResponseEntity<String> checkAccountPIN() {
        val accountNumber = LoggedinUserUtil.getAccountNumber();
        log.info("Checking PIN status for account: {}", accountNumber);

        val isPINValid = accountService.isPinCreated(accountNumber);
        val response = isPINValid ? ApiMessages.PIN_CREATED.getMessage()
                : ApiMessages.PIN_NOT_CREATED.getMessage();

        log.info("PIN status for account {}: {}", accountNumber, response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/pin/create")
    public ResponseEntity<String> createPIN(@RequestBody PinRequest pinRequest) {
        val accountNumber = LoggedinUserUtil.getAccountNumber();
        log.info("Request to create PIN for account: {}", accountNumber);

        accountService.createPin(accountNumber, pinRequest.password(), pinRequest.pin());

        log.info("PIN created successfully for account: {}", accountNumber);
        return ResponseEntity.ok(ApiMessages.PIN_CREATION_SUCCESS.getMessage());
    }

    @PostMapping("/pin/update")
    public ResponseEntity<String> updatePIN(@RequestBody PinUpdateRequest pinUpdateRequest) {
        val accountNumber = LoggedinUserUtil.getAccountNumber();
        log.info("Request to update PIN for account: {}", accountNumber);

        accountService.updatePin(accountNumber, pinUpdateRequest.oldPin(),
                pinUpdateRequest.password(), pinUpdateRequest.newPin());

        log.info("PIN updated successfully for account: {}", accountNumber);
        return ResponseEntity.ok(ApiMessages.PIN_UPDATE_SUCCESS.getMessage());
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> cashDeposit(@RequestBody AmountRequest amountRequest) {
        val accountNumber = LoggedinUserUtil.getAccountNumber();
        log.info("Cash deposit request: account={}, amount={}", accountNumber, amountRequest.amount());

        accountService.cashDeposit(accountNumber, amountRequest.pin(), amountRequest.amount());

        log.info("Cash deposit successful: account={}, amount={}", accountNumber, amountRequest.amount());
        return ResponseEntity.ok(ApiMessages.CASH_DEPOSIT_SUCCESS.getMessage());
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> cashWithdrawal(@RequestBody AmountRequest amountRequest) {
        val accountNumber = LoggedinUserUtil.getAccountNumber();
        log.info("Cash withdrawal request: account={}, amount={}", accountNumber, amountRequest.amount());

        accountService.cashWithdrawal(accountNumber, amountRequest.pin(), amountRequest.amount());

        log.info("Cash withdrawal successful: account={}, amount={}", accountNumber, amountRequest.amount());
        return ResponseEntity.ok(ApiMessages.CASH_WITHDRAWAL_SUCCESS.getMessage());
    }

    @PostMapping("/fund-transfer")
    public ResponseEntity<String> fundTransfer(@RequestBody FundTransferRequest fundTransferRequest) {
        val accountNumber = LoggedinUserUtil.getAccountNumber();
        log.info("Fund transfer request: fromAccount={}, toAccount={}, amount={}",
                accountNumber, fundTransferRequest.targetAccountNumber(), fundTransferRequest.amount());

        accountService.fundTransfer(accountNumber, fundTransferRequest.targetAccountNumber(),
                fundTransferRequest.pin(), fundTransferRequest.amount());

        log.info("Fund transfer successful: fromAccount={}, toAccount={}, amount={}",
                accountNumber, fundTransferRequest.targetAccountNumber(), fundTransferRequest.amount());
        return ResponseEntity.ok(ApiMessages.CASH_TRANSFER_SUCCESS.getMessage());
    }

    @GetMapping("/transactions")
    public ResponseEntity<String> getAllTransactionsByAccountNumber() {
        val accountNumber = LoggedinUserUtil.getAccountNumber();
        log.info("Fetching transactions for account: {}", accountNumber);

        val transactions = transactionService.getAllTransactionsByAccountNumber(accountNumber);
        log.info("Retrieved {} transactions for account {}", transactions.size(), accountNumber);

        return ResponseEntity.ok(JsonUtil.toJson(transactions));
    }

}
