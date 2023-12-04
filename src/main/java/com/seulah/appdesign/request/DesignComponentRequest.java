package com.seulah.appdesign.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DesignComponentRequest {
    private String sizeRedisCard;
    private List<String> components;
}
