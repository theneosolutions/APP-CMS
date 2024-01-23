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
public class SanadGroupStatusDto {
    private String id;
    private String status;
    private String referenceId;
    private List<SanadItemDto> sanadItems;
}
