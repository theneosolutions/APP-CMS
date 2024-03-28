package com.seulah.appdesign.apicontroller.oursms.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
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
