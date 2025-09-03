package ru.test.solution.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.test.solution.enums.OperationType;
import ru.test.solution.model.Transaction;
import ru.test.solution.repository.TransactionRepository;
import ru.test.solution.service.TransactionService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public Transaction createTransaction(UUID walletId, OperationType operationType, BigDecimal amount) {
        Transaction transaction = Transaction.builder()
                .walletId(walletId)
                .operationType(operationType)
                .amount(amount)
                .build();
        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> getTransactionsByWalletId(UUID walletId) {
        return transactionRepository.findByWalletId(walletId);
    }
}
