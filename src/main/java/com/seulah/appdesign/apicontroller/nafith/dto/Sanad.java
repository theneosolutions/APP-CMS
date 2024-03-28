package com.seulah.appdesign.apicontroller.nafith.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Sanad {
    private String id;
    private String number;
    private String status;
    private String reference_id;
    private List<Object> sanad;
}
