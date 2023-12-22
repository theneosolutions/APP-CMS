package com.seulah.appdesign.apicontroller.selaaapi.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OperationsBuyResponse {
    @Id
    @JsonIgnore
    private String id;

    @JsonProperty("ownershipFileUrl")
    private String ownershipFileUrl;

    @JsonProperty("certificates")
    private int certificates;

    @JsonProperty("ownershipId")
    private String ownershipId;

    private String status;
    private String success;
    private String error;
    private String message;

    public OperationsBuyResponse(String ownershipFileUrl, int certificates, String ownershipId, String status, String success, String error, String message) {
        this.ownershipFileUrl = ownershipFileUrl;
        this.certificates = certificates;
        this.ownershipId = ownershipId;
        this.status = status;
        this.success = success;
        this.error = error;
        this.message = message;
    }
}
