package com.wallet.data.repositories;

import com.wallet.data.model.Wallet;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class WalletRepositoryTest {

    @Autowired
    WalletRepository walletRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Find wallet by phone number test")
    void testThatICanFindWalletByPhoneNumber(){
        Wallet wallet = new Wallet();
        wallet.setOwnersPhoneNumber("08077698546");
        walletRepository.save(wallet);
        var savedWallet = walletRepository.findByOwnersPhoneNumber("08077698546");
        log.info(savedWallet.get().getOwnersPhoneNumber());
        assertEquals("08077698546", savedWallet.get().getOwnersPhoneNumber());

    }
}