package com.seulah.appdesign.apicontroller.yakeen;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class YakeenResponseError {
    private boolean owner;
    private String message;
}
