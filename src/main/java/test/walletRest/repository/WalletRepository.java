package test.walletRest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.walletRest.model.Wallet;

import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
}
