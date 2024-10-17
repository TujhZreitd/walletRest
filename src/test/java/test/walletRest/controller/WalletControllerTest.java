package test.walletRest.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import test.walletRest.model.Wallet;
import test.walletRest.repository.WalletRepository;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class WalletControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private WalletRepository walletRepository;

    @BeforeEach
    public void setup() {
        walletRepository.deleteAll();
    }

    @Test
    public void testGetBalance() {
        Wallet wallet = new Wallet(1000);
        wallet = walletRepository.save(wallet);

        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/v1/wallet/" + wallet.getId(), String.class);
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        assertTrue(response.getBody().contains("1000.0"));
    }

    @Test
    public void testGetBalanceWhenWalletNotFoundException() {
        UUID idWalletNotExist = UUID.randomUUID();
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/v1/wallet/" + idWalletNotExist, String.class);
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        assertTrue(response.getBody().contains("Wallet "  + idWalletNotExist + " not found."));
    }

    @Test
    public void testDeposit() {
        Wallet wallet = new Wallet(1000);
        wallet = walletRepository.save(wallet);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String depositJson = "{\"id\" : \"" + wallet.getId() + "\", " + "\"operationType\" : \"DEPOSIT\", \"amount\" : 1000}";
        HttpEntity<String> request = new HttpEntity<>(depositJson, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/api/v1/wallet", request, String.class);
        wallet = walletRepository.findById(wallet.getId()).get();
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        assertTrue(response.getBody().contains("Operation complete"));
        assertThat(wallet.getBalance()).isEqualTo(2000);
    }

    @Test
    public void testDepositWhenWalletNotFoundException() {
        UUID idWalletNotExist = UUID.randomUUID();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String depositJson = "{\"id\" : \"" + idWalletNotExist + "\", " + "\"operationType\" : \"DEPOSIT\", \"amount\" : 1000}";
        HttpEntity<String> request = new HttpEntity<>(depositJson, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/api/v1/wallet", request, String.class);
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        assertTrue(response.getBody().contains("Wallet " + idWalletNotExist + " not found."));
    }

    @Test
    public void testDepositWhenInvalidJsonId() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String depositJson = "{\"operationType\" : \"DEPOSIT\", \"amount\" : 1000}";
        HttpEntity<String> request = new HttpEntity<>(depositJson, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/api/v1/wallet", request, String.class);
        assertThat(HttpStatus.BAD_REQUEST).isEqualTo(response.getStatusCode());
    }

    @Test
    public void testDepositWhenInvalidJsonOperationType() {
        Wallet wallet = new Wallet(1000);
        wallet = walletRepository.save(wallet);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String depositJson = "{\"id\" : \"" + wallet.getId() + "\", " + "\"amount\" : 1000}";
        HttpEntity<String> request = new HttpEntity<>(depositJson, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/api/v1/wallet", request, String.class);
        assertThat(HttpStatus.BAD_REQUEST).isEqualTo(response.getStatusCode());
    }

    @Test
    public void testDepositWhenInvalidJsonAmount() {
        Wallet wallet = new Wallet(1000);
        wallet = walletRepository.save(wallet);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String depositJson = "{\"id\" : \"" + wallet.getId() + "\", " + "\"operationType\" : \"DEPOSIT\", \"amount\" : -1000}";
        HttpEntity<String> request = new HttpEntity<>(depositJson, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/api/v1/wallet", request, String.class);
        assertThat(HttpStatus.BAD_REQUEST).isEqualTo(response.getStatusCode());
    }

    @Test
    public void testWithdraw() {
        Wallet wallet = new Wallet(1000);
        wallet = walletRepository.save(wallet);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String depositJson = "{\"id\" : \"" + wallet.getId() + "\", " + "\"operationType\" : \"WITHDRAW\", \"amount\" : 500}";
        HttpEntity<String> request = new HttpEntity<>(depositJson, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/api/v1/wallet", request, String.class);
        wallet = walletRepository.findById(wallet.getId()).get();
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        assertTrue(response.getBody().contains("Operation complete"));
        assertThat(wallet.getBalance()).isEqualTo(500);
    }

    @Test
    public void testWithdrawWhenInsufficientFunds() {
        Wallet wallet = new Wallet(500);
        wallet = walletRepository.save(wallet);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String depositJson = "{\"id\" : \"" + wallet.getId() + "\", " + "\"operationType\" : \"WITHDRAW\", \"amount\" : 1000}";
        HttpEntity<String> request = new HttpEntity<>(depositJson, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/api/v1/wallet", request, String.class);
        wallet = walletRepository.findById(wallet.getId()).get();
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        assertTrue(response.getBody().contains("Insufficient funds in the wallet."));
        assertThat(wallet.getBalance()).isEqualTo(500);
    }
}