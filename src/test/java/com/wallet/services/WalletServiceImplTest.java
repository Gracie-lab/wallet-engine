package com.wallet.services;

import com.wallet.data.model.Wallet;
import com.wallet.data.repositories.TransactionRepository;
import com.wallet.data.repositories.WalletRepository;
import com.wallet.services.Dtos.*;
import com.wallet.services.exceptions.WalletException;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Slf4j
class WalletServiceImplTest {

    @Autowired
    WalletService walletService;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    @DisplayName("User can only have one wallet")
    void testThatExceptionIsThrownWhenExistingUserTriesToCreateWallet(){
        CreateWalletRequest request = new CreateWalletRequest();
        request.setPhoneNumber("07066176828");
        Assertions.assertThrows(WalletException.class, () ->{
            walletService.createWallet(request);
        });
    }

    @Test
    void createNewWallet() throws WalletException {
        var request = new CreateWalletRequest("08123456789");
        var response = walletService.createWallet(request);
        assertNotNull(walletRepository.findByOwnersPhoneNumber("08123456789").get().getWalletId());
    }

    @Test
    void activateAlreadyActivatedWallet() throws WalletException {
        var request = new CreateWalletRequest("08123456789");
        var response = walletService.createWallet(request);
        var  message = walletService.activateWallet(new ActivateWalletRequest(response.getWalletId()));
        assertEquals("Wallet is already active", message.getMessage());
    }

    @Test
    void testThatICanDeactivateWallet() throws WalletException {
        var request = new CreateWalletRequest("08123456789");
        var response = walletService.createWallet(request);
        walletService.deactivateWallet(new DeactivateWalletRequest(response.getWalletId()));
        assertEquals(false, walletRepository.findByWalletId(response.getWalletId()).get().is_active());
    }

    @Test
    void testThatICanCreditWallet() throws WalletException {
        var request = new CreateWalletRequest("08123456789");
        var response = walletService.createWallet(request);
        walletService.creditWallet(new CreditWalletRequest(response.getWalletId(), 500));
        assertEquals(500, walletRepository.findByWalletId(response.getWalletId()).get().getBalance());
        walletService.creditWallet(new CreditWalletRequest(response.getWalletId(), 250));
        assertEquals(750, walletRepository.findByWalletId(response.getWalletId()).get().getBalance());
    }

    @Test
    void testThatICanDebitWallet() throws WalletException {
        var request = new CreateWalletRequest("08123456789");
        var response = walletService.createWallet(request);
        walletService.creditWallet(new CreditWalletRequest(response.getWalletId(), 500));
        walletService.debitWallet(new DebitWalletRequest(response.getWalletId(), 200));
        assertEquals(300, walletRepository.findByWalletId(response.getWalletId()).get().getBalance());
    }

    @Test
    void testThatTransactionIsLoggedAfterDebitOrCredit() throws WalletException {
        var request = new CreateWalletRequest("08123456789");
        var response = walletService.createWallet(request);
        walletService.creditWallet(new CreditWalletRequest(response.getWalletId(), 500));
        walletService.debitWallet(new DebitWalletRequest(response.getWalletId(), 200));
        assertNotNull(transactionRepository.findByWalletIdAndTransactionType(response.getWalletId(), "DEBIT"));
        assertNotNull(transactionRepository.findByWalletIdAndTransactionType(response.getWalletId(), "CREDIT"));
    }
}