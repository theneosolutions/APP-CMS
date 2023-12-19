package com.seulah.appdesign.entity;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Banner {
    @Id
    private String id;

    private String bannerDesign;

    private String bannerImage;

    private int height;
    private int width;

}
