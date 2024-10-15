package test.walletRest.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import test.walletRest.dto.WalletDto;
import test.walletRest.exception.InsufficientFundsException;
import test.walletRest.exception.WalletNotFoundException;
import test.walletRest.model.OperationType;
import test.walletRest.model.Wallet;
import test.walletRest.repository.WalletRepository;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class SimpleWalletService implements WalletService {

    private final WalletRepository walletRepository;

    @Transactional
    @Override
    public void update(WalletDto walletDto) {
        Optional<Wallet> optionalWallet = walletRepository.findById(walletDto.getId());
        if (optionalWallet.isEmpty()) {
            throw new WalletNotFoundException(walletDto.getId());
        }
        Wallet wallet = optionalWallet.get();
        if (walletDto.getOperationType() == OperationType.DEPOSIT) {
            wallet.setBalance(wallet.getBalance() + walletDto.getAmount());
        } else if (walletDto.getOperationType() == OperationType.WITHDRAW) {
            if (wallet.getBalance() < walletDto.getAmount()) {
                throw new InsufficientFundsException();
            }
            wallet.setBalance(wallet.getBalance() - walletDto.getAmount());
        }
        walletRepository.save(wallet);
    }

    @Override
    public double getBalance(UUID id) {
        Optional<Wallet> optionalWallet = walletRepository.findById(id);
        if (optionalWallet.isEmpty()) {
            throw new WalletNotFoundException(id);
        }
        return optionalWallet.get().getBalance();
    }
}
