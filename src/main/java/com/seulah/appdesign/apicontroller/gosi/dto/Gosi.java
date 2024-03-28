package com.seulah.appdesign.apicontroller.gosi.dto;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author Muhammad Mansoor
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Gosi {
    private String id;
    private String requestNumber;
    private String message;
    private EmploymentStatusInfo[] employmentStatusInfo;
    private String status;
}
