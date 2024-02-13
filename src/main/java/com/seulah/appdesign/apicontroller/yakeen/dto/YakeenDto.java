package com.seulah.appdesign.apicontroller.yakeen.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class YakeenDto {
    private String referenceNumber;
    private String id;
    private String mobile;
    private boolean isOwner;
    private String code;
    private String message;


    public YakeenDto(String referenceNumber, String code, String message) {
        this.referenceNumber = referenceNumber;
        this.code = code;
        this.message = message;
    }
}
