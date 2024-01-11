package com.seulah.appdesign.dto;


import lombok.*;
import org.springframework.stereotype.Service;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ScreenDto {
    private String name;
    private Object components;
}
