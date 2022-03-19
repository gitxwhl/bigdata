package com.raysdata.riskdataanalyzeserver.bean;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Knowledge {
    private String instanceNames;
    private String instanceDescribe;
    private List<String> instanceNameList =new ArrayList<>();
}
