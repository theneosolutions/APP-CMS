package com.seulah.appdesign.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class DesignScreen {
    @Id
    private String id;


    private String applicationName;
    private List<String> screenList;

    private List<DesignComponent> designComponentList;

}
