package test.walletRest.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.walletRest.dto.WalletDto;
import test.walletRest.service.WalletService;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {

    private final WalletService walletService;

    @PostMapping
    public ResponseEntity<String> update(@RequestBody @Valid WalletDto walletDto) {
        walletService.update(walletDto);
        return ResponseEntity.ok("Operation complete");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Double> getBalance(@PathVariable UUID id) {
        return ResponseEntity.ok(walletService.getBalance(id));
    }
}
