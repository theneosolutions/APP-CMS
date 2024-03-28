package com.seulah.appdesign.entity;

import com.seulah.appdesign.dto.ScreenDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document
@AllArgsConstructor
@NoArgsConstructor
public class ScreenFlow {
    @Id
    private String id;
    private List<ScreenDto> screenFlow;
    private String brandId;

}
