package com.seulah.appdesign.apicontroller.gosi.dto;

import lombok.*;

import java.util.List;

/**
 * @author Muhammad Mansoor
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GosiDTO {
    private String requestNumber;
    private String message;
    private List<EmploymentStatusInfo> employmentStatusInfos;
}
