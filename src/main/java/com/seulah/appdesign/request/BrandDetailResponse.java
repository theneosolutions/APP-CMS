package com.seulah.appdesign.request;

import com.seulah.appdesign.entity.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class BrandDetailResponse {

    private Branding brand;
    private BrandingSplashScreen brandingSplashScreen;
    private BrandingColor brandingColor;
    private List<String> brandingLogo;
    private List<String> brandingLayout;
    private List<String> icon;

    public BrandDetailResponse(Branding brand, BrandingSplashScreen brandingSplashScreen, BrandingColor brandingColor,
                               List<String> brandingLogo, List<String> brandingLayout, List<String> brandingIcon) {
        this.brand = brand;
        this.brandingSplashScreen = brandingSplashScreen;
        this.brandingColor = brandingColor;
        this.brandingLogo = brandingLogo;
        this.brandingLayout = brandingLayout;
        this.icon = brandingIcon;
        // Set other fields...
    }

}
