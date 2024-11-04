package com.gerna_v1.banksystem.repositories;

import com.gerna_v1.banksystem.models.entities.SessionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends CrudRepository<SessionEntity, String> {
}