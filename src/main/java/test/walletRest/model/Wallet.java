package test.walletRest.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;
@Data
@Entity
@Table(name = "wallets")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private double balance;

    public Wallet() {

    }

    public Wallet(double balance) {
        this.balance = balance;
    }

    public Wallet(UUID id, double balance) {
        this.id = id;
        this.balance = balance;
    }
}
