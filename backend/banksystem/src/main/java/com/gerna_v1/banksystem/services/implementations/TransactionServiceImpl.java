package com.gerna_v1.banksystem.services.implementations;

import com.gerna_v1.banksystem.models.DTOs.ClientDTO;
import com.gerna_v1.banksystem.models.entities.ClientEntity;
import com.gerna_v1.banksystem.models.entities.TransactionEntity;
import com.gerna_v1.banksystem.repositories.ClientRepository;
import com.gerna_v1.banksystem.repositories.TransactionRepository;
import com.gerna_v1.banksystem.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final ClientRepository clientRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public Optional<ClientDTO> deposit(String username, double amount) {
        Optional<ClientEntity> clientOpt = clientRepository.findByUsername(username);
        if (clientOpt.isPresent()) {
            ClientEntity client = clientOpt.get();
            client.setBalance(client.getBalance() + amount);
            clientRepository.save(client);

            logTransaction(client.getUsername(), client.getUsername(), amount, "DEPOSIT");

            return Optional.of(convertToDTO(client));
        }
        return Optional.empty();
    }

    @Override
    public Optional<ClientDTO> withdraw(String username, double amount) {
        Optional<ClientEntity> clientOpt = clientRepository.findByUsername(username);
        if (clientOpt.isPresent()) {
            ClientEntity client = clientOpt.get();
            if (client.getBalance() >= amount) {
                client.setBalance(client.getBalance() - amount);
                clientRepository.save(client);

                logTransaction(client.getUsername(), client.getUsername(), amount, "WITHDRAW");

                return Optional.of(convertToDTO(client));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<ClientDTO> quickPay(String senderUsername, String receiverPhoneNumber, String receiverGovID, double amount) {
        Optional<ClientEntity> senderOpt = clientRepository.findByUsername(senderUsername);
        Optional<ClientEntity> receiverOpt = clientRepository.findByPhoneAndGovId(receiverPhoneNumber, receiverGovID);
        if (senderOpt.isPresent() && receiverOpt.isPresent()) {
            ClientEntity sender = senderOpt.get();
            ClientEntity receiver = receiverOpt.get();
            if (sender.getBalance() >= amount) {
                sender.setBalance(sender.getBalance() - amount);
                receiver.setBalance(receiver.getBalance() + amount);
                clientRepository.save(sender);
                clientRepository.save(receiver);

                logTransaction(sender.getUsername(), receiver.getUsername(), amount, "QUICK_PAY");

                return Optional.of(convertToDTO(sender));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<ClientDTO> transfer(String senderUsername, String receiverUsername, String receiverGovID, double amount) {
        Optional<ClientEntity> senderOpt = clientRepository.findByUsername(senderUsername);
        Optional<ClientEntity> receiverOpt = clientRepository.findByUsernameAndGovId(receiverUsername, receiverGovID);
        if (senderOpt.isPresent() && receiverOpt.isPresent()) {
            ClientEntity sender = senderOpt.get();
            ClientEntity receiver = receiverOpt.get();
            if (sender.getBalance() >= amount) {
                sender.setBalance(sender.getBalance() - amount);
                receiver.setBalance(receiver.getBalance() + amount);
                clientRepository.save(sender);
                clientRepository.save(receiver);

                logTransaction(sender.getUsername(), receiver.getUsername(), amount, "TRANSFER");

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