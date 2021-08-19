package com.wallet.services;

import com.wallet.data.model.Transaction;
import com.wallet.data.model.TransactionType;
import com.wallet.data.model.Wallet;
import com.wallet.data.repositories.TransactionRepository;
import com.wallet.data.repositories.WalletRepository;
import com.wallet.services.Dtos.*;
import com.wallet.services.exceptions.WalletException;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@Slf4j
public class WalletServiceImpl implements WalletService{

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    TransactionRepository transactionRepository;

    String walletcode = "WEN";

    @Override
    public Wallet createWallet(CreateWalletRequest request) throws WalletException {
        log.info(request.getPhoneNumber());
        var wallet = walletRepository.findByOwnersPhoneNumber(request.getPhoneNumber());

        if(wallet.isPresent()) throw new WalletException("User with this phone number already has a wallet");

        Wallet newWallet = new Wallet();
        newWallet.setOwnersPhoneNumber(request.getPhoneNumber());
        newWallet.setWalletId(walletcode + RandomString.make(5).toUpperCase());
        return walletRepository.save(newWallet);
    }

    @Override
    public GeneralResponse activateWallet(ActivateWalletRequest request) throws WalletException {
        var wallet = walletRepository.findByWalletId(request.getWalletId());
        if(wallet.isEmpty()) throw new WalletException("No wallet with give id");
        if(wallet.get().is_active()) return new GeneralResponse("Wallet is already active");
        wallet.get().set_active(true);
        walletRepository.save(wallet.get());
        return new GeneralResponse("Wallet is now active");
    }

    @Override
    public GeneralResponse deactivateWallet(DeactivateWalletRequest request) throws WalletException {
        var wallet = walletRepository.findByWalletId(request.getWalletId());
        if(wallet.isEmpty()) throw new WalletException("No wallet with give id");
        if(!wallet.get().is_active()) return new GeneralResponse("Wallet has already been deactivated");
        wallet.get().set_active(false);
        wallet.get().setDateDeactivated(LocalDateTime.now());
        walletRepository.save(wallet.get());
        return new GeneralResponse("Wallet is been deactivated successfully");
    }

    @Override
    public GeneralResponse creditWallet(CreditWalletRequest request) throws WalletException {
        var wallet = walletRepository.findByWalletId(request.getWalletId());
        if(wallet.isEmpty()) throw new WalletException("No wallet with given id");
        if(!wallet.get().is_active()) return new GeneralResponse("This wallet is not active");
        var newBalance = wallet.get().getBalance()+request.getAmount();
        wallet.get().setBalance(newBalance);
        walletRepository.save(wallet.get());
        saveTransaction(request.getWalletId(), request.getAmount(), TransactionType.CREDIT);
        return new GeneralResponse("Transaction successful");
    }

    @Override
    public GeneralResponse debitWallet(DebitWalletRequest request) throws WalletException {
        var wallet = walletRepository.findByWalletId(request.getWalletId());
        if(wallet.isEmpty()) throw new WalletException("No wallet with given id");
        if(!wallet.get().is_active()) return new GeneralResponse("This wallet is not active");
        var walletBalance = wallet.get().getBalance();
        if(walletBalance < request.getAmount()) return  new GeneralResponse("Insufficient balance");
        var newBalance = wallet.get().getBalance()-request.getAmount();
        wallet.get().setBalance(newBalance);
        walletRepository.save(wallet.get());
        saveTransaction(request.getWalletId(), request.getAmount(), TransactionType.DEBIT);
        return new GeneralResponse("Transaction successful");
    }

    private void saveTransaction(String walletId, double amount, TransactionType transactionType){
        Transaction transaction = new Transaction();
        transaction.setTransactionType(transactionType.name());
        transaction.setWalletId(walletId);
        transaction.setAmount(amount);
        transaction.setDate(LocalDateTime.now());
        transactionRepository.save(transaction);
    }
}
