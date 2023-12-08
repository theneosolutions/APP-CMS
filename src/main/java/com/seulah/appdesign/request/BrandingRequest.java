package com.seulah.appdesign.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BrandingRequest {


    private String splashScreen;

    private String content;
    private List<String> color;

    private String layout;
}
