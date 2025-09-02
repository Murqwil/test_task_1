package ru.test.solution.service;

import ru.test.solution.enums.OperationType;
import ru.test.solution.model.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface TransactionService {
    Transaction createTransaction(UUID walletId, OperationType operationType, BigDecimal amount);
    List<Transaction> getTransactionsByWalletId(UUID walletId);
}
