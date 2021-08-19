package com.wallet.data.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String ownersPhoneNumber;
    private String walletId;
    private double balance;
    private boolean is_active = true;
    private LocalDateTime dateCreated = LocalDateTime.now();
    private LocalDateTime dateDeactivated;
}
