package com.seulah.appdesign.apicontroller.selaaapi.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OperationsTransferRequest {
    private String name;
    private String civilId;
    private String phone;
    private String ownershipId;
}
