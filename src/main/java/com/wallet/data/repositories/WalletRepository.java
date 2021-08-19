package com.wallet.data.repositories;

import com.wallet.data.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface WalletRepository extends JpaRepository<Wallet, Integer> {

    Optional<Wallet> findByOwnersPhoneNumber(String phoneNumber);
    Optional<Wallet> findByWalletId(String wallerId);
}
