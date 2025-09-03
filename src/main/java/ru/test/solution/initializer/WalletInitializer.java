package ru.test.solution.initializer;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.test.solution.exception.WalletAlreadyExistsException;
import ru.test.solution.service.WalletService;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@AllArgsConstructor
public class WalletInitializer {

    private final WalletService walletService;

    @PostConstruct
    public void init() {
        UUID defaultWalletId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        BigDecimal initialBalance = BigDecimal.ZERO;

        try {
            walletService.createWallet(defaultWalletId, initialBalance);
        } catch (WalletAlreadyExistsException e) {
            System.out.println("Default wallet already exists");
        }
    }
}
