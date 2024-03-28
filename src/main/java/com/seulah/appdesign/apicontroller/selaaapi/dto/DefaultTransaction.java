package com.seulah.appdesign.apicontroller.selaaapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DefaultTransaction {
    @Id
    @JsonIgnore
    private String id;
    private String tid;
    private String dateTime;

    private String amount;

    @JsonProperty("certificates")
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
