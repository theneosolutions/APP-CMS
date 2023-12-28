package com.seulah.appdesign.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NavigationsRequest {
    private String navbar;

    private String bottomTab;

    private String drawer;


    private String langCode;
}
