package com.seulah.appdesign.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandingLayoutRequest {
    private String height;
    private String width;

    private String lottieFiles;
}
