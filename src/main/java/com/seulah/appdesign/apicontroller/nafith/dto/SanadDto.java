package com.seulah.appdesign.apicontroller.nafith.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class SanadDto {
    private String dueType;
    private String dueDate;
    private double totalValue;
}
