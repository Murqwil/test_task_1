package ru.test.solution.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.test.solution.model.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByWalletId(UUID walletId);
}
