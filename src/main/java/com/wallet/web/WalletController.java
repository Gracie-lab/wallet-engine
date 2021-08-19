package com.wallet.web;

import com.wallet.data.model.Wallet;
import com.wallet.services.Dtos.*;
import com.wallet.services.WalletService;
import com.wallet.services.exceptions.WalletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    WalletService walletService;

    @PostMapping("/create-wallet")
    public ResponseEntity<?> createWallet(CreateWalletRequest request) throws WalletException {
        try{
        Wallet response = walletService.createWallet(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (WalletException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.UNAUTHORIZED);

        }
    }

    @PostMapping("activate-wallet")
    public ResponseEntity<?> activateWallet(ActivateWalletRequest request) throws WalletException {
        try{
            GeneralResponse response = walletService.activateWallet(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (WalletException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);

        }

    }

    @PostMapping("deactivate-wallet")
    public ResponseEntity<?> deactivateWallet(DeactivateWalletRequest request) throws WalletException {
        try {
            GeneralResponse response = walletService.deactivateWallet(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (WalletException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.UNAUTHORIZED);

        }
    }

    @PostMapping("debit-wallet")
    public ResponseEntity<?> debitWallet(DebitWalletRequest request) throws WalletException {
        try {
            var response = walletService.debitWallet(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (WalletException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.UNAUTHORIZED);

        }
    }

    @PostMapping("credit-wallet")
    public ResponseEntity<?> creditWallet(CreditWalletRequest request) throws WalletException {
        try {
            var response = walletService.creditWallet(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (WalletException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.UNAUTHORIZED);

        }
    }
}
