package com.seulah.appdesign.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LayoutDetail {
    private String brandId;
    private byte[] iconContent;
    private byte[] lottieContent;
}