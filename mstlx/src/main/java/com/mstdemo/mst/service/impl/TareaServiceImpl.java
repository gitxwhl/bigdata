package com.mstdemo.mst.service.impl;


import com.mstdemo.mst.bean.Tarea;
import com.mstdemo.mst.mapper.TareaMapper;
import com.mstdemo.mst.service.TareaService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TareaServiceImpl implements TareaService {
    @Autowired
   private TareaMapper tareaMapper;


    /**
     * 递归查询地区级联
     */

   /* public List<Tarea> findTarea(){
        //查询所有父级菜单
        List<Tarea> areaParentList = tareaMapper.findParentTarea();
        for (Tarea area :areaParentList){
            //根据父级code查询父级下面子集
            List<Tarea> are = tareaMapper.findChildTarea(area.getCode());
            //组合父节点和子节点
            area.setChildList(are);
            //组合完之后，are节点下面还会有子节点，所以以上逻辑还需要再执行一次，所以用到了递归：代码修改如下：

        }
        return null;
    }*/


    /**
     * 递归调用
     */
    @Override
    public List<Tarea> findTare(){
        Tarea tarea=new Tarea();
        tarea.setCode("1");
        findChildTarea(tarea);
        return tarea.getChildList();
    }

    /**
     * 逻辑相同 递归子集调用自己
     * @param parentTarea
     * @return
     */
    public void findChildTarea(Tarea parentTarea){
        List<Tarea> childList=null;
        if(parentTarea.getCode().equals("1")){
            childList = tareaMapper.findParentTarea();
        }else {
            //查询所有省即，父级为空的值
            childList = tareaMapper.findChildTarea((parentTarea.getCode()));
        }
        for (Tarea area :childList){
            findChildTarea(area);
        }
        //组合父级和子集的关系
        parentTarea.setChildList(childList);
    }

    /**
     * 方法二: map的方式方式实现
     */

    @Override
    public List<Tarea> findTareaMap(){
        List<Tarea> tareaList=new ArrayList<>();
        //获取所有的地区数据
        List<Tarea> tareas =  tareaMapper.findTateaAll();
        //将地区的code和每一个地区数据关联
        Map<String,Tarea> map=new HashMap<>();
        for(Tarea are : tareas){
            map.put(are.getCode(),are);
        }

        //获取所有的父级放入集合
        for(Tarea are :tareas){
            Tarea child = are;
            if(child.getParentCode()==null){
                tareaList.add(child);
            }else {
                //根据code获取所有的子集
              Tarea tareaparent  = map.get(child.getParentCode());
                //组合子集和父级的关系
                tareaparent.getChildList().add(child);
            }
        }
        return tareaList;
    }





}
