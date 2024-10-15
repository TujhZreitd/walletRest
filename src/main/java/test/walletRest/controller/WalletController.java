package test.walletRest.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
    public ResponseEntity<String> update(@RequestBody @Valid WalletDto walletDto, BindingResult result) {
        if (result.hasErrors()) {
            FieldError error = result.getFieldError();
            if (error != null) {
                return ResponseEntity.badRequest().body("Ошибка валидации: " + error.getDefaultMessage());
            }
        }
        walletService.update(walletDto);
        return ResponseEntity.ok("Operation complete");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Double> getBalance(@PathVariable UUID id) {
        return ResponseEntity.ok(walletService.getBalance(id));
    }
}
