package com.seulah.appdesign.apicontroller.selaaapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DefaultHistoryMain {
    @Id
    @JsonIgnore
    private String dId;
    private String id;
    private String action;
    private String type;
    private String lenderId;
    private String amount;
    private String dateTime;
    private String createdAt;
    private String updatedAt;
    private String intent;
    private Transaction transaction;

    public DefaultHistoryMain(String id, String action, String type, String lenderId, String amount, String dateTime, String createdAt, String updatedAt, String intent, Transaction transaction) {
        this.id = id;
        this.action = action;
        this.type = type;
        this.lenderId = lenderId;
        this.amount = amount;
        this.dateTime = dateTime;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.intent = intent;
        this.transaction = transaction;
    }
}
