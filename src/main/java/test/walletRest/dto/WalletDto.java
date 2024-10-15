package test.walletRest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import test.walletRest.model.OperationType;

import java.util.UUID;

public class WalletDto {
    @NotNull(message = "Id must be format UUID")
    private UUID id;
    @NotNull(message = "OperationType must be DEPOSIT or WITHDRAW ")
    private OperationType operationType;
    @Min(value = 1, message = "Amount must be greater than 0")
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
