package com.raysdata.riskdataanalyzeserver.bean;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Instanceinfo {
    private String instanceId;
    private String typeName;
    private String instanceCode;
    private String instanceDescribe;
    private String instanceName;

}
