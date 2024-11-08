package com.gerna_v1.banksystem.repositories;

import com.gerna_v1.banksystem.models.entities.TransactionEntity;
import lombok.NonNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends MongoRepository<TransactionEntity, String> {
    List<TransactionEntity> findAll();
}

