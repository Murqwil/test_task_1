package ru.test.solution.exception;

import java.util.UUID;

public class WalletNotFoundException extends RuntimeException {
  public WalletNotFoundException(String message) {
    super(message);
  }

  public WalletNotFoundException(UUID walletId) {
    super("Wallet not found with id: " + walletId);
  }
}