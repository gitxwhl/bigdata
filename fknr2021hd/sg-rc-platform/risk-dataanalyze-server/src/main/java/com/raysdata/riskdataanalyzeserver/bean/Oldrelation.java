package com.raysdata.riskdataanalyzeserver.bean;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Data
public class Oldrelation {
    private Integer instanceName;
    private Integer relationId;
    private Integer instanceId;
}
