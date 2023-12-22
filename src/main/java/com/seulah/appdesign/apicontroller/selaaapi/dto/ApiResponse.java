package com.seulah.appdesign.apicontroller.selaaapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ApiResponse {
    private String status;
    private String success;
    private String error;
    @JsonIgnore
    private String message;
}
