package com.gerna_v1.banksystem.services;

import com.gerna_v1.banksystem.models.DTOs.ClientDTO;
import com.gerna_v1.banksystem.models.entities.TransactionEntity;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    Optional<ClientDTO> deposit(String username, double amount);
    Optional<ClientDTO> withdraw(String username, double amount);
    Optional<ClientDTO> quickPay(String senderUsername, String receiverPhoneNumber, String receiverGovID, double amount);
    Optional<ClientDTO> transfer(String senderUsername, String receiverUsername, String receiverGovID, double amount);
    Optional<List<TransactionEntity>> getAllTransactions();
    Optional<TransactionEntity> getTransactionById(String id);
}