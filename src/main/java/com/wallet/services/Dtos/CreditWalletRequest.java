package com.wallet.services.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreditWalletRequest {
    private String walletId;
    private double amount;
}
