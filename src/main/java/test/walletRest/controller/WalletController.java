package test.walletRest.controller;

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
    public void update(@RequestBody WalletDto walletDto) {
        walletService.update(walletDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Double> getBalance(@PathVariable UUID id) {
        return ResponseEntity.ok(walletService.getBalance(id));
    }
}
