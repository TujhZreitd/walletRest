package test.walletRest.dto;

import test.walletRest.model.OperationType;

import java.util.UUID;

public class WalletDto {
    private UUID id;
    private OperationType operationType;
    private double amount;

    public UUID getId() {
        return id;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
