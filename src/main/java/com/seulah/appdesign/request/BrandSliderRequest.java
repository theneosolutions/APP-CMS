package com.seulah.appdesign.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandSliderRequest {
    private String title;
    private String desc;
    private MultipartFile file;
    private String position;
}
