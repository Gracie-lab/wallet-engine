package com.wallet.services.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DebitWalletRequest {
    private String walletId;
    private double amount;
}
