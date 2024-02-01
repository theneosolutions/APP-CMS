package com.seulah.appdesign.apicontroller.oursms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SmsRequest {
    private String src;
    private String[] dests;
    private String body;
    private int priority;
    private int delay;
    private int validity;
    private int maxParts;
    private int dlr;
    private int prevDups;
    private String msgClass;
}
