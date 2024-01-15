package com.seulah.appdesign.apicontroller.gosi.dto;

import lombok.*;

/**
 * @author Muhammad Mansoor
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmploymentStatusInfo {
    private String fullName;
    private double basicWage;
    private double housingAllowance;
    private double otherAllowance;
    private String employerName;
    private int workingMonths;
    private String employmentStatus;
}
