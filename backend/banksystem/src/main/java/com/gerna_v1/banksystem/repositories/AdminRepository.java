package com.gerna_v1.banksystem.repositories;

import com.gerna_v1.banksystem.models.entities.AdminEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends MongoRepository<AdminEntity, String> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<AdminEntity> findByUsername(String username);
    Optional<AdminEntity> findByEmail(String email);
    List<AdminEntity> getAdminEntitiesByAccessLevel(int accessLevel);
    List<AdminEntity> findByName(String name);
    List<AdminEntity> findAll();
}
