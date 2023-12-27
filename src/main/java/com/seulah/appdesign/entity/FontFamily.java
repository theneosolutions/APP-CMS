package com.seulah.appdesign.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

/**
 * @author Muhammad Mansoor
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FontFamily {
    @Id
    private String id;

    private String brandId;

    private Object response;
}
