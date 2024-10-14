package test.walletRest.service;

import test.walletRest.dto.WalletDto;

import java.util.UUID;

public interface WalletService {
    void update(WalletDto walletDto);

    double getBalance(UUID id);
}
