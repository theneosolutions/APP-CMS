package com.seulah.appdesign.dto;


import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ScreenDto {
    private String name;
    private Object components;
    private List<Object> button;
}
