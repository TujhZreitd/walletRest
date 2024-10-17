/*
package test.walletRest.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import test.walletRest.dto.WalletDto;
import test.walletRest.exception.InsufficientFundsException;
import test.walletRest.exception.WalletNotFoundException;
import test.walletRest.model.OperationType;
import test.walletRest.model.Wallet;
import test.walletRest.repository.WalletRepository;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimpleWalletServiceTest {
    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private SimpleWalletService walletService;

    @Test
    public void testDeposit() {
        Wallet wallet = new Wallet(UUID.randomUUID(), 1000);

        when(entityManager.find(Wallet.class, wallet.getId())).thenReturn(wallet);

        walletService.update(new WalletDto(wallet.getId(), OperationType.DEPOSIT, 1000));
        assertThat(wallet.getBalance()).isEqualTo(2000);
        verify(entityManager, times(1)).persist(wallet);
    }

    @Test
    public void testDeopsitWithWalletNotFoundException() {
        UUID idWalletNotExists = UUID.randomUUID();

        when(walletRepository.findById(idWalletNotExists)).thenReturn(Optional.empty());

        assertThrows(WalletNotFoundException.class,
                () -> walletService.update(new WalletDto(idWalletNotExists, OperationType.DEPOSIT, 2000)));
    }

    @Test
    public void testWithdraw() {
        Wallet wallet = new Wallet(UUID.randomUUID(), 1000);

        when(walletRepository.findById(wallet.getId())).thenReturn(Optional.of(wallet));

        walletService.update(new WalletDto(wallet.getId(), OperationType.WITHDRAW, 500));
        assertThat(wallet.getBalance()).isEqualTo(500);
        verify(walletRepository, times(1)).save(wallet);
    }

    @Test
    public void testWithdrawWithInsufficientFundsException() {
        Wallet wallet = new Wallet(UUID.randomUUID(), 1000);

        when(walletRepository.findById(wallet.getId())).thenReturn(Optional.of(wallet));

        assertThrows(InsufficientFundsException.class,
                () -> walletService.update(new WalletDto(wallet.getId(), OperationType.WITHDRAW, 1500)));
        assertThat(wallet.getBalance()).isEqualTo(1000);
        verify(walletRepository, times(0)).save(wallet);
    }

}*/
