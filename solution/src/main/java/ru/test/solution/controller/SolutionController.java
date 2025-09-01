package ru.test.solution.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.test.solution.enums.OperationType;

@RestController
@RequestMapping("/api/v1")
public class SolutionController {

    @PostMapping("/wallet")
    void changeWallet(long walletId, OperationType operationType, Long amount) { // тут обработку сделать необходимо

    }

    @GetMapping("/wallets")
    void getWallets() { // Тут необходимо сделать будет пагинацию.

    }
}
