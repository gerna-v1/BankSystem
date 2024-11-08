package com.gerna_v1.banksystem.repositories;

import com.gerna_v1.banksystem.models.entities.TokenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends CrudRepository<TokenEntity, String> {
    Optional<TokenEntity> findByToken(String token);
    void deleteByToken(String token);

    List<TokenEntity> findAllValidIsFalseOrRevokedIsFalseById(String id);
}