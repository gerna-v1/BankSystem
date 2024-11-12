package com.gerna_v1.banksystem.repositories;

import com.gerna_v1.banksystem.models.DTOs.ClientDTO;
import com.gerna_v1.banksystem.models.entities.ClientEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends MongoRepository<ClientEntity, String> {
    List<ClientEntity> findByName(String name);
    Optional<ClientEntity> findByUsername(String username);
    Optional<ClientEntity> findByUuid(String uuid);
    Optional<ClientEntity> findByEmail(String email);

    boolean existsByUuid(String uuid);

    Optional<ClientEntity> findByPhoneAndGovId(String phoneNumber, String govID);
    Optional<ClientEntity> findByUsernameAndGovId(String username, String govID);

    Optional<ClientEntity> findTopByOrderByBalance();
    Optional<ClientEntity> findTopByOrderByBalanceAsc();

    List<ClientEntity> findByBalanceGreaterThan(double balance);
    Optional<ClientEntity> findClientEntityByUsername(String username);
    List<ClientEntity> findAll();
}
