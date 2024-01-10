package com.mstdemo.mst.service;

import com.mstdemo.mst.bean.Tarea;

import java.util.List;

public interface TareaService {
    /**
     * 递归调用
     */
    public List<Tarea> findTare();

    /**
     * map方式获取ztree
     */
    List<Tarea>  findTareaMap();
}
