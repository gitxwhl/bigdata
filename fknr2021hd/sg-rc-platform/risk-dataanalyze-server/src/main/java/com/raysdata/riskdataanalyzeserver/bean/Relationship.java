package com.raysdata.riskdataanalyzeserver.bean;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Relationship {
    //作业引发人员
    private String operationInitiator;
    //关联关系
    private String glgxsOperationInitiator;

    //作业引发人员
    private String  distributionEvent;
    //关联关系
    private String glgxDistributionEvent;


    //外包单位隐含人员
    private String  wbDwyhyy;
    //关联关系
    private String wbDwyhyyGlgx;


    //外包单位承担风险
    private String  wbDwCdfx;
    //关联关系
    private String wbDwCdfxGlgx;



    //判断清空
    private String flag="0";





}
