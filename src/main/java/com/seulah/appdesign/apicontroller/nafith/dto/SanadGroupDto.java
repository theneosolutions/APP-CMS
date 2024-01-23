package com.seulah.appdesign.apicontroller.nafith.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class SanadGroupDto {
    private String debtorNin;
    private String cityOfIssuance;
    private String debtorPhoneNumber;
    private double totalValue;
    private int maxApproveDuration;
    private List<SanadDto> sanadList;

}
