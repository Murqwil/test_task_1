package ru.test.solution.data.reqeust;

import ru.test.solution.enums.OperationType;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionRequest (
        UUID walletId,
        OperationType operationType,
        BigDecimal amount
)
{}
