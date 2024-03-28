package com.seulah.appdesign.entity;

import lombok.*;
import nonapi.io.github.classgraph.json.*;
import org.springframework.data.mongodb.core.mapping.*;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandingLayout {
    @Id
    private String id;
    private String brandId;

    private String name;
    private String lottieFiles;
}
