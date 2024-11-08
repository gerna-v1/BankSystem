package com.gerna_v1.banksystem.models.entities;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionEntity {
    private String id;
    private String sender;
    private String receiver;
    private double amount;
    private String type;
    private String date;
}
