package com.seulah.appdesign.apicontroller.selaaapi.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OperationsBuyRequest {
    private int amount;
    private String internalId;
}
