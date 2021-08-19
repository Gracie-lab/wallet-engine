package com.wallet.services;

import com.wallet.data.model.Wallet;
import com.wallet.services.Dtos.*;
import com.wallet.services.exceptions.WalletException;
import org.springframework.stereotype.Service;

@Service
public interface WalletService {

     Wallet createWallet(CreateWalletRequest request) throws WalletException;
     GeneralResponse activateWallet(ActivateWalletRequest request) throws WalletException;
     GeneralResponse deactivateWallet(DeactivateWalletRequest request) throws WalletException;
     GeneralResponse creditWallet(CreditWalletRequest request) throws WalletException;
     GeneralResponse debitWallet(DebitWalletRequest request) throws WalletException;


}
