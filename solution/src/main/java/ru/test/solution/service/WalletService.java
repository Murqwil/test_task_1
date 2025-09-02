package ru.test.solution.service;

import ru.test.solution.enums.OperationType;
import ru.test.solution.model.Wallet;

import java.math.BigDecimal;
import java.util.UUID;

public interface WalletService {
    Wallet getWallet(UUID walletId);
    Wallet processTransaction(UUID walletId, OperationType operationType, BigDecimal amount);
    Wallet createWallet(UUID walletId, BigDecimal initialBalance);
}
