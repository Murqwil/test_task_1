package ru.test.solution.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.test.solution.enums.OperationType;
import ru.test.solution.exception.InsufficientFundsException;
import ru.test.solution.exception.WalletAlreadyExistsException;
import ru.test.solution.exception.WalletNotFoundException;
import ru.test.solution.model.Wallet;
import ru.test.solution.repository.WalletRepository;
import ru.test.solution.service.WalletService;


import java.math.BigDecimal;
import java.util.UUID;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Transactional(readOnly = true)
    public Wallet getWallet(UUID walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found with id: " + walletId));
    }

    @Transactional
    @Retryable(
            retryFor = { org.springframework.orm.ObjectOptimisticLockingFailureException.class },
            maxAttempts = 10,
            backoff = @Backoff(delay = 100, multiplier = 2)
    )
    public Wallet processTransaction(UUID walletId, OperationType operationType, BigDecimal amount) {
        Wallet wallet = walletRepository.findByIdWithLock(walletId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found with id: " + walletId));

        BigDecimal newBalance;
        if (operationType == OperationType.DEPOSIT) {
            newBalance = wallet.getBalance().add(amount);
        } else {
            if (wallet.getBalance().compareTo(amount) < 0) {
                throw new InsufficientFundsException("Insufficient funds for withdrawal");
            }
            newBalance = wallet.getBalance().subtract(amount);
        }

        wallet.setBalance(newBalance);
        return walletRepository.save(wallet);
    }

    @Transactional
    public Wallet createWallet(UUID walletId, BigDecimal initialBalance) {
        boolean isExist = walletRepository.existsById(walletId);
        if (isExist) {
          throw new WalletAlreadyExistsException("Wallet already exists");
        }
        Wallet wallet = Wallet.builder()
                .id(walletId)
                .balance(initialBalance)
                .build();
        return walletRepository.save(wallet);
    }
}
