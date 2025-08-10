package com.webapp.bankingportal.service;

import com.webapp.bankingportal.model.response.AccountResponse;
import com.webapp.bankingportal.model.response.UserResponse;

public interface DashboardService {
    UserResponse getUserDetails(String accountNumber);
    AccountResponse getAccountDetails(String accountNumber);
}