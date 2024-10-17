package test.walletRest.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import test.walletRest.dto.WalletDto;
import test.walletRest.exception.InsufficientFundsException;
import test.walletRest.exception.WalletNotFoundException;
import test.walletRest.model.OperationType;
import test.walletRest.model.Wallet;

import java.util.UUID;

@AllArgsConstructor
@Service
public class SimpleWalletService implements WalletService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void update(WalletDto walletDto) {
        Wallet wallet = entityManager.find(Wallet.class, walletDto.getId(), LockModeType.PESSIMISTIC_WRITE);
        if (wallet == null) {
            throw new WalletNotFoundException(walletDto.getId());
        }
        if (walletDto.getOperationType() == OperationType.DEPOSIT) {
            wallet.setBalance(wallet.getBalance() + walletDto.getAmount());
        } else if (walletDto.getOperationType() == OperationType.WITHDRAW) {
            if (wallet.getBalance() < walletDto.getAmount()) {
                throw new InsufficientFundsException();
            }
            wallet.setBalance(wallet.getBalance() - walletDto.getAmount());
        }
        entityManager.merge(wallet);
    }

    @Override
    public double getBalance(UUID id) {
        Wallet wallet = entityManager.find(Wallet.class, id);
        if (wallet == null) {
            throw new WalletNotFoundException(id);
        }
        return wallet.getBalance();
    }
}
