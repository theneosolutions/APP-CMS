package com.seulah.appdesign.apicontroller.document.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanTypeCalculation {
    private String formulaName;
    private Long loanTypeId;
    private double loanAmount;
    private double interestRatio;
    private String firstInstallmentDate;
    private String lastInstallmentDate;
    private double installmentPerMonth;
    private int month;
    private double amountAfterInterest;
    private double installmentPerMonthAfterInterest;
    private double amountAfterInterestAndTex;
    private double installmentPerMonthAfterInterestAndTex;
    private double processingFee;
    private double vatOnFee;
    private String userId;
    private double totalFee;
    private String maturityDate;
    private String screenName;
    private String status;
}
