package test.walletRest.exception;

import java.util.UUID;

public class WalletNotFoundException extends RuntimeException {
    public WalletNotFoundException(UUID id) {
        super("Wallet " + id + " not found.");
    }
}
