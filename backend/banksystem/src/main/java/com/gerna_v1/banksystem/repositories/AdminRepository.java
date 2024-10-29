package com.gerna_v1.banksystem.repositories;

import com.gerna_v1.banksystem.models.entities.AdminEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends MongoRepository<AdminEntity, String> {
    AdminEntity saveAdmin(AdminEntity adminEntity);
    boolean adminExists(String email);
}
