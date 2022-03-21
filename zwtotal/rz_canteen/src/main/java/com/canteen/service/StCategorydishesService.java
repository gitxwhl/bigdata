package com.canteen.service;

import com.canteen.entity.StCategorydishes;
import com.canteen.utils.PageBean;
import com.canteen.utils.Results;

import java.util.List;

public interface StCategorydishesService {

    List<StCategorydishes> findStCategorydishes();
    /**
     * 菜品类别分页查询分页
     * @return
     */
    PageBean<StCategorydishes> getStCategorydishesPage(PageBean<StCategorydishes> pageBean,String varietiesName ,String varietiesCode);
    PageBean<StCategorydishes> getStCategorydishesPage(PageBean<StCategorydishes> pageBean,String varietiesName ,String varietiesCode,Integer id);
    /**
     * 菜品类别删除
     * @return
     */
     boolean delByIds(Integer id);

    /**
     * 重载删除方法
     * @param id
     * @param type 1强制删除，并且删除子类 ，0正常删除
     * @return
     */
    Results<Boolean> delByIds(Integer id,int type);
    /**
     * 添加类别
     * @return
     */
    boolean saveStCategorydishesFist(StCategorydishes stCategorydishes);
    public Results<Boolean> saveStCategorydishes(StCategorydishes stCategorydishes);
    /**
     * 修改类别
     * @return
     */
    boolean updateStCategorydishesFist(StCategorydishes stCategorydishes);
    /**
     * 根据id获取菜品类别列表
     * @return
     */
    StCategorydishes findStCategorydishesbyID(Integer id);

    Results<Boolean> updateStCategorydishesFists(StCategorydishes stCategorydishes);

    Results<Boolean> updateStCategorydishesState(int id, int delFlag);
}