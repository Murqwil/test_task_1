package ru.test.solution.exception;

public class WalletNotFoundException extends RuntimeException {
  public WalletNotFoundException(String message) {
    super(message);
  }
}
