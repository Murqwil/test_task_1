package ru.test.solution.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.test.solution.data.reqeust.TransactionRequest;
import ru.test.solution.data.response.WalletResponse;
import ru.test.solution.model.Wallet;
import ru.test.solution.service.TransactionService;
import ru.test.solution.service.WalletService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class WalletController {

    private final WalletService walletService;
    private final TransactionService transactionService;

    public WalletController(WalletService walletService, TransactionService transactionService) {
        this.walletService = walletService;
        this.transactionService = transactionService;
    }

    @PostMapping("/wallet")
    public ResponseEntity<WalletResponse> processTransaction(@RequestBody TransactionRequest request) {
        Wallet wallet = walletService.processTransaction(
                request.walletId(),
                request.operationType(),
                request.amount()
        );

        transactionService.createTransaction(
                request.walletId(),
                request.operationType(),
                request.amount()
        );

        return ResponseEntity.ok(new WalletResponse(wallet.getId(), wallet.getBalance()));
    }

    @GetMapping("/wallets/{walletId}")
    public ResponseEntity<WalletResponse> getWalletBalance(@PathVariable UUID walletId) {
        Wallet wallet = walletService.getWallet(walletId);
        return ResponseEntity.ok(new WalletResponse(wallet.getId(), wallet.getBalance()));
    }
}
