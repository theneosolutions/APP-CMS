package com.seulah.appdesign.apicontroller.selaaapi.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OperationsTransferResponse {
    @Id
    @JsonIgnore
    private String id;
    private String ownershipFileUrl;
    private String ownershipId;
    private String certificates;

    private String status;
    private String success;
    private String error;
    private String message;

    public OperationsTransferResponse(String ownershipFileUrl, String ownershipId, String certificates, String status, String success, String error, String message) {
        this.ownershipFileUrl = ownershipFileUrl;
        this.ownershipId = ownershipId;
        this.certificates = certificates;
        this.status = status;
        this.success = success;
        this.error = error;
        this.message = message;
    }
}
