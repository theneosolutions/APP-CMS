package com.seulah.appdesign.apicontroller.nafith.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CallBackDetails {
    @Id
    private String id;
    private Object callback;

    public CallBackDetails(Object callback) {
        this.callback = callback;
    }
}
