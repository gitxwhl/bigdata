package com.raysdata.riskdataanalyzeserver.bean;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Data
@AllArgsConstructor
public class EventType {
    private String personalEvents;
    private String NetworkEvents;
    private String DeviceEvents;
    private String GridEvents;
}
