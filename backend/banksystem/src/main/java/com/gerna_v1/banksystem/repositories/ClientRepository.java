package com.gerna_v1.banksystem.repositories;

import com.gerna_v1.banksystem.models.entities.ClientEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends MongoRepository<ClientEntity, String> {
    List<ClientEntity> findByName(String name);
    List<ClientEntity> findByEmail(String email);
    List<ClientEntity> findByBalanceGreaterThan(double balance);
    List<ClientEntity> findAll();
}
