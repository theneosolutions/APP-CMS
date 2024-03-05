package com.seulah.appdesign.apicontroller.lean.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LeanRequest {
    private String type;
    private String identification_number;
    private String iban;
}
