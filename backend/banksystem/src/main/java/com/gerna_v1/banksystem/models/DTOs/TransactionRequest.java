package com.gerna_v1.banksystem.models.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionRequest {
    private String senderUsername;
    private String receiverUsername;
    private String receiverIdentifier;
    private String receiverGovID;
    private double amount;
}