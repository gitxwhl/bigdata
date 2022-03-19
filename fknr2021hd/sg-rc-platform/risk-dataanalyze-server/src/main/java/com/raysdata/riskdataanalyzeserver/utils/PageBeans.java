package com.raysdata.riskdataanalyzeserver.utils;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class PageBeans<T>  {
    //每页条数
    private Integer pageSize;
    //总记录数
    private Integer totalRecord;
    //请求当前页：pageNum
    private Integer pageNum;
    //集合
    private List<T> list;

    private Map map;
    //计算出来
    //总页数
    private Integer totalPage;
    //起始坐标
    private Integer startIndex;

    public Integer getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Integer totalRecord) {
        this.totalRecord = totalRecord;
        if(totalRecord%pageSize==0){
           totalPage= totalRecord/pageSize;
        }else {
            totalPage= totalRecord/pageSize + 1;
        }
    }
    public Integer getStartIndex() {
        return (pageNum-1)*pageSize;
    }
    @JsonBackReference
    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }


    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
    public void setMap(Map map) {
        this.map = map;
    }
    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

}
