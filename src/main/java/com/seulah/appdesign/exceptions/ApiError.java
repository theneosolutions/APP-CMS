package com.seulah.appdesign.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiError {
    private HttpStatus status;
    private String message;
}
