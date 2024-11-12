package com.gerna_v1.banksystem.services.implementations;

import com.gerna_v1.banksystem.models.DTOs.ClientDTO;
import com.gerna_v1.banksystem.models.DTOs.TransactionRequest;
import com.gerna_v1.banksystem.models.entities.ClientEntity;
import com.gerna_v1.banksystem.models.entities.TransactionEntity;
import com.gerna_v1.banksystem.repositories.TransactionRepository;
import com.gerna_v1.banksystem.services.ClientService;
import com.gerna_v1.banksystem.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final ClientService clientService;
    private final TransactionRepository transactionRepository;

    @Override
    public Optional<ClientDTO> deposit(TransactionRequest request) {
        Optional<ClientEntity> clientOpt = clientService.findByUsername(request.getSenderUsername());
        if (clientOpt.isPresent()) {
            ClientEntity client = clientOpt.get();
            client.setBalance(client.getBalance() + request.getAmount());
            clientService.save(client);

            logTransaction(client.getUsername(), client.getUsername(), request.getAmount(), "DEPOSIT");

            return Optional.of(convertToDTO(client));
        }
        return Optional.empty();
    }

    @Override
    public Optional<ClientDTO> withdraw(TransactionRequest request) {
        Optional<ClientEntity> clientOpt = clientService.findByUsername(request.getSenderUsername());
        if (clientOpt.isPresent()) {
            ClientEntity client = clientOpt.get();
            if (client.getBalance() >= request.getAmount()) {
                client.setBalance(client.getBalance() - request.getAmount());
                clientService.save(client);

                logTransaction(client.getUsername(), client.getUsername(), request.getAmount(), "WITHDRAW");

                return Optional.of(convertToDTO(client));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<ClientDTO> quickPay(TransactionRequest request) {
        Optional<ClientEntity> senderOpt = clientService.findByUsername(request.getSenderUsername());
        Optional<ClientEntity> receiverOpt = clientService.findByPhoneAndGovId(request.getReceiverIdentifier(), request.getReceiverGovID());
        if (senderOpt.isPresent() && receiverOpt.isPresent()) {
            ClientEntity sender = senderOpt.get();
            ClientEntity receiver = receiverOpt.get();
            if (sender.getBalance() >= request.getAmount()) {
                sender.setBalance(sender.getBalance() - request.getAmount());
                receiver.setBalance(receiver.getBalance() + request.getAmount());
                clientService.save(sender);
                clientService.save(receiver);

                logTransaction(sender.getUsername(), receiver.getUsername(), request.getAmount(), "QUICK_PAY");

                return Optional.of(convertToDTO(sender));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<ClientDTO> transfer(TransactionRequest request) {
        Optional<ClientEntity> senderOpt = clientService.findByUsername(request.getSenderUsername());
        Optional<ClientEntity> receiverOpt = clientService.findByUsernameAndGovId(request.getReceiverUsername(), request.getReceiverGovID());
        if (senderOpt.isPresent() && receiverOpt.isPresent()) {
            ClientEntity sender = senderOpt.get();
            ClientEntity receiver = receiverOpt.get();
            if (sender.getBalance() >= request.getAmount()) {
                sender.setBalance(sender.getBalance() - request.getAmount());
                receiver.setBalance(receiver.getBalance() + request.getAmount());
                clientService.save(sender);
                clientService.save(receiver);

                logTransaction(sender.getUsername(), receiver.getUsername(), request.getAmount(), "TRANSFER");

                return Optional.of(convertToDTO(sender));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<TransactionEntity>> getAllTransactions() {
        return Optional.of(transactionRepository.findAll());
    }

    @Override
    public Optional<TransactionEntity> getTransactionById(String id) {
        return transactionRepository.findById(id);
    }

    private void logTransaction(String sender, String receiver, double amount, String type) {
        TransactionEntity transaction = TransactionEntity.builder()
                .sender(sender)
                .receiver(receiver)
                .amount(amount)
                .type(type)
                .date(LocalDateTime.now().toString())
                .build();
        transactionRepository.save(transaction);
    }

    private ClientDTO convertToDTO(ClientEntity entity) {
        return ClientDTO.builder()
                .name(entity.getName())
                .lastName(entity.getLastName())
                .username(entity.getUsername())
                .balance(entity.getBalance())
                .email(entity.getEmail())
                .govId(entity.getGovId())
                .build();
    }
}