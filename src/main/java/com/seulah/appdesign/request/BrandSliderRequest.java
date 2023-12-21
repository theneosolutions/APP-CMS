package com.seulah.appdesign.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class BrandSliderRequest {
    private String title;
    private String desc;
    private MultipartFile file;
    private String position;
}
