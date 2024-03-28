package com.seulah.appdesign.entity;

import lombok.*;
import nonapi.io.github.classgraph.json.*;
import org.springframework.data.mongodb.core.mapping.*;

/**
 * @author Muhammad Mansoor
 */

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandingFont {
    @Id
    private String id;
    private String brandId;
    private String name;
    private String font;
}
