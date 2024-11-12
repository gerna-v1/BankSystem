package com.gerna_v1.banksystem.controllers;

import com.gerna_v1.banksystem.models.DTOs.ApiResponse;
import com.gerna_v1.banksystem.models.DTOs.ClientDTO;
import com.gerna_v1.banksystem.models.DTOs.TransactionRequest;
import com.gerna_v1.banksystem.models.entities.TransactionEntity;
import com.gerna_v1.banksystem.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ApiResponse<ClientDTO>> deposit(@RequestBody TransactionRequest request) {
        Optional<ClientDTO> client = transactionService.deposit(request);
        return client.map(value -> ResponseEntity.ok(ApiResponse.<ClientDTO>builder()
                        .success(true)
                        .data(value)
                        .build()))
                .orElseGet(() -> ResponseEntity.status(400).body(ApiResponse.<ClientDTO>builder()
                        .success(false)
                        .message("Deposit failed")
                        .build()));
    }

    @PostMapping("/withdraw")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ApiResponse<ClientDTO>> withdraw(@RequestBody TransactionRequest request) {
        Optional<ClientDTO> client = transactionService.withdraw(request);
        return client.map(value -> ResponseEntity.ok(ApiResponse.<ClientDTO>builder()
                        .success(true)
                        .data(value)
                        .build()))
                .orElseGet(() -> ResponseEntity.status(400).body(ApiResponse.<ClientDTO>builder()
                        .success(false)
                        .message("Withdraw failed")
                        .build()));
    }

    @PostMapping("/quickPay")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ApiResponse<ClientDTO>> quickPay(@RequestBody TransactionRequest request) {
        Optional<ClientDTO> client = transactionService.quickPay(request);
        return client.map(value -> ResponseEntity.ok(ApiResponse.<ClientDTO>builder()
                        .success(true)
                        .data(value)
                        .build()))
                .orElseGet(() -> ResponseEntity.status(400).body(ApiResponse.<ClientDTO>builder()
                        .success(false)
                        .message("QuickPay failed")
                        .build()));
    }

    @PostMapping("/transfer")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ApiResponse<ClientDTO>> transfer(@RequestBody TransactionRequest request) {
        Optional<ClientDTO> client = transactionService.transfer(request);
        return client.map(value -> ResponseEntity.ok(ApiResponse.<ClientDTO>builder()
                        .success(true)
                        .data(value)
                        .build()))
                .orElseGet(() -> ResponseEntity.status(400).body(ApiResponse.<ClientDTO>builder()
                        .success(false)
                        .message("Transfer failed")
                        .build()));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<TransactionEntity>>> getAllTransactions() {
        Optional<List<TransactionEntity>> transactions = transactionService.getAllTransactions();
        return transactions.map(value -> ResponseEntity.ok(ApiResponse.<List<TransactionEntity>>builder()
                        .success(true)
                        .data(value)
                        .build()))
                .orElseGet(() -> ResponseEntity.status(404).body(ApiResponse.<List<TransactionEntity>>builder()
                        .success(false)
                        .message("No transactions found")
                        .build()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<TransactionEntity>> getTransactionById(@PathVariable String id) {
        Optional<TransactionEntity> transaction = transactionService.getTransactionById(id);
        return transaction.map(value -> ResponseEntity.ok(ApiResponse.<TransactionEntity>builder()
                        .success(true)
                        .data(value)
                        .build()))
                .orElseGet(() -> ResponseEntity.status(404).body(ApiResponse.<TransactionEntity>builder()
                        .success(false)
                        .message("Transaction not found")
                        .build()));
    }
}