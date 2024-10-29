package com.gerna_v1.banksystem.services;

import com.gerna_v1.banksystem.models.DTOs.PasswordDTO;
import com.gerna_v1.banksystem.models.entities.PasswordEntity;
import org.springframework.stereotype.Component;

@Component
public class PasswordMapper {
    public PasswordEntity passwordDTOToPasswordEntity(PasswordDTO passwordDTO) {
        return PasswordEntity.builder()
                .hash(passwordDTO.getHash())
                .salt(passwordDTO.getSalt())
                .build();
    }

    public PasswordDTO passwordEntityToPasswordDTO(PasswordEntity passwordEntity) {
        return PasswordDTO.builder()
                .hash(passwordEntity.getHash())
                .salt(passwordEntity.getSalt())
                .build();
    }
}
