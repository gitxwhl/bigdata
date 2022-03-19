package com.raysdata.riskdataanalyzeserver.bean;

import com.raysdata.riskdataanalyzeserver.utils.ExcelColumn;
import lombok.*;

/**
 * ImportTempVO
 * <p>通过index来确定单元格的位置</p>
 *
 * @author 阿落学Java
 */
@Getter
@Setter
@NoArgsConstructor
@Data
@AllArgsConstructor
public class ImportTempVO{

    @ExcelColumn("关系名称(1:下发2:隐含3：存在4：导致5：巡视6：资质等级7：引发8：承担9：操作不当10：被管理11：属于)")
    private String relationName;

    @ExcelColumn("源类别(1;作业计划2:外包单位3:人员4:风险5:隐患6:事件7:违章信息)")
    private String sourceType;

    @ExcelColumn("目标类别(1;作业计划2:外包单位3:人员4:风险5:隐患6:事件7:违章信息)")
    private String targetType;

    @ExcelColumn("关联关系类型(1:1对一2：一对多)")
    private String relationType;

    @ExcelColumn("描述")
    private String relationDescribe;

}
