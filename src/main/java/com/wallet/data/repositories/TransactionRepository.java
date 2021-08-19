package com.wallet.data.repositories;

import com.wallet.data.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Optional<Transaction> findByWalletId(String walletId);
    Optional<Transaction> findByWalletIdAndTransactionType(String walletId, String transactionType);
}
