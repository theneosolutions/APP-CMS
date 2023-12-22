package com.seulah.appdesign.apicontroller.selaaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private String id;
    private String dateTime;
    private String amount;
    private String certificates;
    private String lenderId;
    private String lendersCustomerId;
    private String lenderInternalId;
    private String type;
    private String owner;
    private String active;
    private String ownershipFileUrl;
    private String ownershipId;
    private String wallet;
    private String redeemAllowed;
    private String createdAt;
    private String updatedAt;
}
