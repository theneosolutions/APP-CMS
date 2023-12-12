package com.seulah.appdesign.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandingLayout {
    @Id
    private String id;
    private String height;
    private String width;

    private String icon;
    private String lottieFiles;
}
