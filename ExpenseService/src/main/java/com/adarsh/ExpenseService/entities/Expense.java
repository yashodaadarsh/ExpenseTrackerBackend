package com.adarsh.ExpenseService.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String externalId;

    private String userId;

    @Nonnull
    private String amount;

    private String merchant;

    private String currency;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    @PrePersist
    @PreUpdate
    private void prePersistAndPreUpdateCallback(){
        if( this.externalId == null ){
            this.externalId = UUID.randomUUID().toString();
        }
        if( this.createdAt == null ){
            this.createdAt = new Timestamp(System.currentTimeMillis());
        }

        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }


}
