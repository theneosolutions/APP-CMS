package com.seulah.appdesign.apicontroller.selaaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WalletBalance {
    private String success;
    private String amount;
    private String reducing;
    private String locked;
    private String dateTime;
}
