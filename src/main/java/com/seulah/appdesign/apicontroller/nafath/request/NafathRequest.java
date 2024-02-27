package com.seulah.appdesign.apicontroller.nafath.request;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NafathRequest {
    private String nationalId;
    private String service;
}
