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
    private String id;
    private String mobile;
    private String isOwner;
    private String code;
    private String referenceNumber;
    private String message;
}
