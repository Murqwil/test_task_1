package ru.test.solution.data.response;

import java.math.BigDecimal;
import java.util.UUID;

public record WalletResponse (
   UUID id,
   BigDecimal balance
) {}
