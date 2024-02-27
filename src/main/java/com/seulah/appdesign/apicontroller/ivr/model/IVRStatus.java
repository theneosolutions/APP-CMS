package com.seulah.appdesign.apicontroller.ivr.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class IVRStatus{
    private String id;
    private int status;
    private String mobile;

    public IVRStatus(int status, String mobile) {
        this.status = status;
        this.mobile = mobile;
    }
}
