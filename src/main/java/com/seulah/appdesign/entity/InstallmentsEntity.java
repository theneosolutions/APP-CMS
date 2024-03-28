package com.seulah.appdesign.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document
public class InstallmentsEntity {
    @Id
    private String id;
    private String title;
    private String desc;
    private String price;
    private String months;
    private String url;

    public InstallmentsEntity(String title, String desc, String price, String months) {
        this.title = title;
        this.desc = desc;
        this.price = price;
        this.months = months;
    }
}
