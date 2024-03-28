package com.seulah.appdesign.apicontroller.nafath.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class NafathPayload {
    @Id
    private String id;
    private String transId;
    private Object nafathPayLoad;

    public NafathPayload(String transId, Object nafathPayLoad) {
        this.transId = transId;
        this.nafathPayLoad = nafathPayLoad;
    }
}
