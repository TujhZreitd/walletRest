package test.walletRest.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import test.walletRest.dto.WalletDto;
import test.walletRest.model.OperationType;
import test.walletRest.model.Wallet;
import test.walletRest.repository.WalletRepository;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class SimpleWalletService implements WalletService {

    private final WalletRepository walletRepository;

    @Override
    public void update(WalletDto walletDto) {
        Optional<Wallet> optionalWallet = walletRepository.findById(walletDto.getId());
        if (optionalWallet.isEmpty()) {
            throw new IllegalArgumentException("Wallet not found");
        }
        Wallet wallet = optionalWallet.get();
        if (walletDto.getOperationType() == OperationType.DEPOSIT) {
            wallet.setBalance(wallet.getBalance() + walletDto.getAmount());
        } else if (walletDto.getOperationType() == OperationType.WITHDRAW) {
            if (wallet.getBalance() < walletDto.getAmount()) {
                throw new IllegalArgumentException("Insufficient funds");
            }
            wallet.setBalance(wallet.getBalance() - walletDto.getAmount());
        }
        walletRepository.save(wallet);
    }

    @Override
    public double getBalance(UUID id) {
        Optional<Wallet> optionalWallet = walletRepository.findById(id);
        if (optionalWallet.isEmpty()) {
            throw new IllegalArgumentException("Wallet not found");
        }
        return optionalWallet.get().getBalance();
    }
}
