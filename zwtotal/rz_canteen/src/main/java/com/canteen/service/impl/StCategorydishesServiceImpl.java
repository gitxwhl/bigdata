package com.canteen.service.impl;

import com.canteen.constant.BaseCode;
import com.canteen.entity.StCategorydishes;
import com.canteen.mapper.StCategorydishesMapper;
import com.canteen.mapper.StOperationrestaurantMapper;
import com.canteen.service.StCategorydishesService;
import com.canteen.utils.PageBean;
import com.canteen.utils.Results;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StCategorydishesServiceImpl implements StCategorydishesService {

    @Autowired
   private StCategorydishesMapper stCategorydishesMapper;

    @Autowired
    private StOperationrestaurantMapper tOperationrestaurantMapper;


    /**
     * 菜品类别树状列表,如需要分页这需要传入所在等级
     * @return
     */
    @Override
    public List<StCategorydishes> findStCategorydishes() {

        List<StCategorydishes> list = stCategorydishesMapper.findStCategorydishes();


        return buildTree(list);
    }


    private static List<StCategorydishes> buildTree(List<StCategorydishes> nodes) {
        Map<Integer, List<StCategorydishes>> sub = nodes.stream().filter(node -> node.getParentIds() != 0).collect(Collectors.groupingBy(node -> node.getParentIds()));
        nodes.forEach(node -> node.setSubclass(sub.get(node.getId())));
        return nodes.stream().filter(node -> node.getParentIds() == 0).collect(Collectors.toList());
    }

    private  static  List<Integer>  getChildId(List<StCategorydishes> childs ,Integer level,Integer id){
        Map<Integer,Integer> map = new HashMap<>();
        map.put(id,id);
        if(childs.size()>0){
            Integer  maxLevel = childs.get(childs.size()-1).getLevel();
            do{
                level ++;
                for(StCategorydishes st: childs){
                    if(st.getLevel()==level&&map.containsKey(st.getParentIds())){
                        map.put(st.getId(),st.getId());
                    }
                }
            }while (level<maxLevel);
        }
        List<Integer> listKey =new ArrayList<Integer>();// map的key集合
        Iterator iterator = map.keySet().iterator();
        while(iterator.hasNext()){
            Integer key =(Integer) iterator.next();
            listKey.add(key); //key存入list
        }
        return listKey;
    }


    /**
     * 菜品类别分页查询分页
     * @return
     */
    @Override
    public PageBean<StCategorydishes> getStCategorydishesPage(PageBean<StCategorydishes> pageBean, String varietiesName, String varietiesCode) {
        List<StCategorydishes> list=stCategorydishesMapper.getStCategorydishesList((pageBean.getPageNum()-1)*pageBean.getPageSize(), pageBean.getPageSize(),varietiesName,varietiesCode);
        pageBean.setList(list);
        int count=stCategorydishesMapper.getStCategorydishesCount(varietiesName,varietiesCode);
        pageBean.setTotalRecord(count);
        return pageBean;
    }
    public PageBean<StCategorydishes> getStCategorydishesPage(PageBean<StCategorydishes> pageBean, String varietiesName, String varietiesCode,Integer id) {
        //id为空或者0查询全部
        if(id==null||id==0){
            return  getStCategorydishesPage( pageBean, varietiesName,varietiesCode);
        }
        //查询id所在的等级
        StCategorydishes stlevel = stCategorydishesMapper.findStCategorydishesbyID(id);
        if(stlevel.getLevel()<1){
            return  getStCategorydishesPage( pageBean, varietiesName,varietiesCode);
        }
        //查询当前数据的及子数据的id;
        //当前数据不多不用写函数，代码里实现
        List<StCategorydishes> list = stCategorydishesMapper.findStCategorydishesBylevel(stlevel.getLevel());
          List<Integer>  ids =   getChildId (list,stlevel.getLevel(),id);
          String  idsStr = StringUtils.join(ids,",");

        List<StCategorydishes> resultList =stCategorydishesMapper.getStCategorydishesList2((pageBean.getPageNum()-1)*pageBean.getPageSize(), pageBean.getPageSize(),varietiesName,varietiesCode,idsStr);
        pageBean.setList(resultList);
        int count=stCategorydishesMapper.getStCategorydishesCount2(varietiesName,varietiesCode,idsStr);
        pageBean.setTotalRecord(count);
        return pageBean;
    }


    /**
     * 根据id删除菜品类别
     * @return
     */
    @Override
    public boolean delByIds(Integer id) {
        int count =  stCategorydishesMapper.isExistChild(id);
        if(count>0){
            return  false;
        }
        return stCategorydishesMapper.delByIds(id);
    }

    /**
     * 后期需要修改
     * @param id
     * @param type
     * @return
     */
    public Results<Boolean> delByIds(Integer id, int type) {
        /**
         * 此处判断是否有子，有子不能修改
         */
        int count =  stCategorydishesMapper.isExistChild(id);
        if(count>0){
            return  new Results<Boolean> (BaseCode.BaseResultCode.FAILE,BaseCode.Categorydishes.EXIST_CHILD,false);
        }
        /**
         * TODO  这里还要判断菜单，此处逻辑待定
         */
        boolean falg = stCategorydishesMapper.delByIds(id);
        return new Results<Boolean> (BaseCode.BaseResultCode.SUCSESS,"",falg);
    }
    /**
     * 添加类别
     * TODO 不懂对应的业务逻辑，重新构造方法
     * @return
     */
    @Override
    public boolean saveStCategorydishesFist(StCategorydishes stCategorydishes) {
        if(stCategorydishes.getLevel()==1){
            stCategorydishes.setParentIds(1);
           return stCategorydishesMapper.saveStCategorydishesFist(stCategorydishes);
        }else{
            return stCategorydishesMapper.saveStCategorydishesFist(stCategorydishes);
        }
    }

    public Results<Boolean> saveStCategorydishes(StCategorydishes stCategorydishes) {

        StCategorydishes stparents = stCategorydishesMapper.findStCategorydishesbyID(stCategorydishes.getParentIds());
        if(stparents ==null){
            return new Results<Boolean> (BaseCode.BaseResultCode.FAILE,"parentIds错误",false);
        }
        stCategorydishes.setLevel(stparents.getLevel() + 1);
        //当判断为空时，获取小于1为错误值，
        if(stCategorydishes.getLevel()==null||stCategorydishes.getLevel()<=0){
            return new Results<Boolean> (BaseCode.BaseResultCode.FAILE,"level值错误",false);
            //等于1表示第一层
        }else {
            //level 在父的级别上加1

            stCategorydishes.setCreateDate(new Date());
            stCategorydishes.setUpdateTime(new Date());
            stCategorydishes.setDelFlag("0");
            stCategorydishes.setCreateBy("admin");
            stCategorydishes.setUpdateBy("admin");
            stCategorydishes.setSort(1l);
            return new Results<Boolean>(BaseCode.BaseResultCode.SUCSESS,
                    "数据保存", stCategorydishesMapper.saveStCategorydishesFist(stCategorydishes));
        }
    }

    /**
     * 修改类别
     * @return
     */
    @Override
    public boolean updateStCategorydishesFist(StCategorydishes stCategorydishes) {
        return stCategorydishesMapper.updateStCategorydishesFist(stCategorydishes);
    }

    /**
     * 修改方法重载
     * @param st
     * @return
     */
    @Override
    public Results<Boolean> updateStCategorydishesFists(StCategorydishes st) {
        //判断数据库里 prentids和level不能在这里修改. 修改这两项会造成数据库树错位
        StCategorydishes  oldst = stCategorydishesMapper.findStCategorydishesbyID(st.getId());
        if(oldst == null||!String.valueOf(st.getParentIds()).equals(String.valueOf(oldst.getParentIds()))){
            return new Results<Boolean> (BaseCode.BaseResultCode.FAILE,
                    "数据异常",false);
        }
        //st.setUpdateTime(new Date());
        boolean   result = stCategorydishesMapper.updateStCategorydishesFist(st);
        if(result){
            return new Results<Boolean> (BaseCode.BaseResultCode.SUCSESS,
                    "更新成功",result);
        }else {
            return new Results<Boolean> (BaseCode.BaseResultCode.FAILE,
                    "数据异常",result);
        }

    }

    /**
     * 启停操作
     * @param id  数据id
     * @param delFlag  启停表示
     * @return
     */
    @Override
    public Results<Boolean> updateStCategorydishesState(int id, int delFlag) {
        //不能操作delFlag =1
        if(delFlag==1){
            return new Results<Boolean> (BaseCode.BaseResultCode.FAILE,
                    "数据异常",false);
        }

        boolean   result = stCategorydishesMapper.updateState(id,delFlag);
        if(result){
            return new Results<Boolean> (BaseCode.BaseResultCode.SUCSESS,
                    "更新成功",result);
        }else {
            return new Results<Boolean> (BaseCode.BaseResultCode.FAILE,
                    "数据异常",result);
        }
    }

    /**
     * 根据id获取菜品类别列表
     * @return
     */
    @Override
    public StCategorydishes findStCategorydishesbyID(Integer id) {
        return stCategorydishesMapper.findStCategorydishesbyID(id);
    }


}
