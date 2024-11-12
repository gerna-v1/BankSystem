package com.gerna_v1.banksystem.services;

import com.gerna_v1.banksystem.models.DTOs.ClientDTO;
import com.gerna_v1.banksystem.models.DTOs.TransactionRequest;
import com.gerna_v1.banksystem.models.entities.TransactionEntity;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    Optional<ClientDTO> deposit(TransactionRequest request);
    Optional<ClientDTO> withdraw(TransactionRequest request);
    Optional<ClientDTO> quickPay(TransactionRequest request);
    Optional<ClientDTO> transfer(TransactionRequest request);
    Optional<List<TransactionEntity>> getAllTransactions();
    Optional<TransactionEntity> getTransactionById(String id);
}