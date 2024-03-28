package com.seulah.appdesign.request;

import com.seulah.appdesign.entity.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandDetailResponse {
    private String brandId;
    private Branding branding;
    private String brandingSplashScreen;
    private BrandingColor brandingColor;
    private List<LayoutDetail> brandingLayoutDetails;

    private HashMap<String,Object> brandingLogoDetail;
}


