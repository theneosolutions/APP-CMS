package com.seulah.appdesign.apicontroller.nafath.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Document
public class NafathResponse {
    @Id
    private String id;
    private String token;
    private String transId;
    private String requestId;

    public NafathResponse(String token, String transId, String requestId) {
        this.token = token;
        this.transId = transId;
        this.requestId = requestId;
    }
}
