package com.seulah.appdesign.entity;

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
public class DesignComponent {
    @Id
    private String id;

    private String sizeRedisCard;
    private String components;

    private String languageCode;
}
