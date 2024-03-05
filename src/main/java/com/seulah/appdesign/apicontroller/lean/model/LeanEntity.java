package com.seulah.appdesign.apicontroller.lean.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document
public class LeanEntity {
    @Id
    private String id;
    private String status;
    private String resultsId;
    private String message;
    private String timestamp;
    private String meta; // Using Object as the type since it's null in the example
    private String statusDetail; // Similarly, using Object for the null field
    private Object verifications;
}
