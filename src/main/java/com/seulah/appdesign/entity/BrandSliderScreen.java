package com.seulah.appdesign.entity;


import com.seulah.appdesign.request.BrandSliderRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nonapi.io.github.classgraph.json.Id;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandSliderScreen {
    @Id
    private String id;
    private String mainTittle;
    private String brandId;
    private List<BrandSliderRequest> brandSliderScreenList;

    public BrandSliderScreen(String mainTittle,  List<BrandSliderRequest> brandSliderScreenList,String brandId) {
        this.mainTittle = mainTittle;
        this.brandId = brandId;
        this.brandSliderScreenList = brandSliderScreenList;
    }
}
