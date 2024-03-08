package com.seulah.appdesign.apicontroller.document.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanResponse {
    private String message;
    private LoanTypeCalculation data;
    private boolean error;
}
