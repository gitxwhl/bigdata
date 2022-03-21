package com.canteen.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.canteen.entity.StDishevaluation;
import com.canteen.mapper.StDishesAnalysisMapper;
import com.canteen.service.StDishesAnalysisService;
import com.canteen.utils.FileUpload;
import com.canteen.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class StDishesAnalysisServiceImpl implements StDishesAnalysisService {
    @Autowired
    StDishesAnalysisMapper stDishesAnalysisMapper;

    /*
    餐馆评价查询
     */
    public List selectAnalysisOfRestaurant(String restaurant,String strDate,String endDate,String name,String filtercomments) {
        List<Map> list =  stDishesAnalysisMapper.selectAnalysisOfRestaurant(restaurant,strDate,endDate,name,filtercomments);
        int verysatisfied = 0;
        int satisfied = 0;
        int commonly = 0;
        int dissatisfied = 0;
        int verydissatisfied = 0;

        int verysatisfied1 = 0;
        int satisfied1 = 0;
        int commonly1 = 0;
        int dissatisfied1 = 0;
        int verydissatisfied1 = 0;

        int verysatisfied2 = 0;
        int satisfied2 = 0;
        int commonly2 = 0;
        int dissatisfied2 = 0;
        int verydissatisfied2 = 0;

        for(Map map : list){
            if("5".equals(map.get("environment").toString())){
              verysatisfied = verysatisfied + 1;
            }else if("4".equals(map.get("environment").toString())){
                satisfied = satisfied + 1;
            }else if("3".equals(map.get("environment").toString())){
                commonly = commonly + 1;
            }else if("2".equals(map.get("environment").toString())){
                dissatisfied = dissatisfied + 1;
            } else if("1".equals(map.get("environment").toString())){
                verydissatisfied = verydissatisfied + 1;
            }
            if("5".equals(map.get("overallevaluation").toString())){
                verysatisfied1 = verysatisfied1 + 1;
            }else if("4".equals(map.get("overallevaluation").toString())){
                satisfied1 = satisfied1 + 1;
            }else if("3".equals(map.get("overallevaluation").toString())){
                commonly1 = commonly1 + 1;
            }else if("2".equals(map.get("overallevaluation").toString())){
                dissatisfied1 = dissatisfied1 + 1;
            } else if("1".equals(map.get("overallevaluation").toString())){
                verydissatisfied1 = verydissatisfied1 + 1;
            }

            if("5".equals(map.get("canteenservice").toString())){
                verysatisfied2 = verysatisfied2 + 1;
            }else if("4".equals(map.get("canteenservice").toString())){
                satisfied2 = satisfied2 + 1;
            }else if("3".equals(map.get("canteenservice").toString())){
                commonly2 = commonly2 + 1;
            }else if("2".equals(map.get("canteenservice").toString())){
                dissatisfied2 = dissatisfied2 + 1;
            } else if("1".equals(map.get("canteenservice").toString())){
                verydissatisfied2 = verydissatisfied2 + 1;
            }
        }
        Map limap = new HashMap();
        List list1 = new ArrayList();
        limap.put("name","用餐环境");
        limap.put("verysatisfied",verysatisfied);
        limap.put("satisfied",satisfied);
        limap.put("commonly",commonly);
        limap.put("dissatisfied",dissatisfied);
        limap.put("verydissatisfied",verydissatisfied);
        limap.put("total",verysatisfied+satisfied+commonly+dissatisfied+verydissatisfied);
        list1.add(limap);
        Map limap1 = new HashMap();
        limap1.put("name","整体评价");
        limap1.put("verysatisfied",verysatisfied1);
        limap1.put("satisfied",satisfied1);
        limap1.put("commonly",commonly1);
        limap1.put("dissatisfied",dissatisfied1);
        limap1.put("verydissatisfied",verydissatisfied1);
        limap1.put("total",verysatisfied1+satisfied1+commonly1+dissatisfied1+verydissatisfied1);
        list1.add(limap1);
        Map limap2 = new HashMap();
        limap2.put("name","食堂服务");
        limap2.put("verysatisfied",verysatisfied2);
        limap2.put("satisfied",satisfied2);
        limap2.put("commonly",commonly2);
        limap2.put("dissatisfied",dissatisfied2);
        limap2.put("verydissatisfied",verydissatisfied2);
        limap2.put("total",verysatisfied2+satisfied2+commonly2+dissatisfied2+verydissatisfied2);
        list1.add(limap2);
        return list1;
    }

    /*
    菜品整体评价查询
     */
    public PageBean selectAnalysisOfMyFoods(String param){
        JSONObject Param = JSONObject.parseObject(param);
        String strDate = Param.getString("strDate");
        String endDate = Param.getString("endDate");
        String restaurant = Param.getString("restaurant");
        String name = Param.getString("name");
        int pageNum = Integer.parseInt(Param.getString("pageNum"));
        int pageSize = Integer.parseInt(Param.getString("pageSize"));
        String filtercomments = Param.getString("filtercomments");
        if(filtercomments.length() == 1){
            insertFilterComments(param);
        }
        Map map = new HashMap();
        List list = new ArrayList();
        List Dislist  = new ArrayList();
        int totalSize =stDishesAnalysisMapper.getTotals(restaurant,strDate,endDate,name,filtercomments);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if( totalSize == 0 ){
            //pageBean.setList(new ArrayList());
            pageBean.setMap(new HashMap());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List<Map> equipmentList = stDishesAnalysisMapper.selectAnalysisOfMyFoods(restaurant,strDate,endDate,name,filtercomments,startIndex,pageSize);
            for(Map map1 : equipmentList){
                  Map DisMap = new HashMap();
                //String[] strs = null;
                  String uploadpictures = (String) map1.get("uploadpictures");
                      if (uploadpictures != null) {
                          String[] strs = uploadpictures.split(",");
                          DisMap.put("uploadpictures",strs);
                      }else {
                          int[] st = new int[0];
                          DisMap.put("uploadpictures",st);
                      }

                //DisMap.put("uploadpictures",strs);
                DisMap.put("date1",map1);
                DisMap.put("date",stDishesAnalysisMapper.selectDishesScore((Integer) map1.get("id")));
                Dislist.add(DisMap);
            }

            selectIds(restaurant,strDate,endDate,name,startIndex,pageSize,filtercomments);
            List list1 = stDishesAnalysisMapper.selectAnalysisOfMyFood();
            map.put("Foods",list1);
            map.put("AnalysisOfMyFoods",Dislist);
            map.put("AnalysisOfRestaurant",selectAnalysisOfRestaurant(restaurant,strDate,endDate,name,filtercomments));
            list.add(map);
            pageBean.setMap(map);
        }
        return pageBean;
    }

    /*
    菜品评价数统计
     */
    public void selectIds(String restaurant,String strDate,String endDate,String name,int startIndex,int pageSize,String filtercomments){
        //每次获取不同时间段菜品评价数量时先清空上次表中的数据
        stDishesAnalysisMapper.deleteScore();
        List map = stDishesAnalysisMapper.selectIds(restaurant,strDate,endDate,name,startIndex,pageSize,filtercomments);
        for(Object id1 : map) {
            List<Map> list = stDishesAnalysisMapper.selectScore((Integer) id1);
            for (Map ma : list) {
                int id = (int) ma.get("disid");
                if (stDishesAnalysisMapper.selectById(id) == null) {
                    stDishesAnalysisMapper.insertId(id);
                    if ("5".equals(ma.get("score").toString())) {
                        stDishesAnalysisMapper.updateDishevaluation(id);
                    } else if ("4".equals(ma.get("score").toString())) {
                        stDishesAnalysisMapper.updateSatisfied(id);
                    } else if ("3".equals(ma.get("score").toString())) {
                        stDishesAnalysisMapper.updateCommonly(id);
                    } else if ("2".equals(ma.get("score").toString())) {
                        stDishesAnalysisMapper.updateDissatisfied(id);
                    } else if ("1".equals(ma.get("score").toString())) {
                        stDishesAnalysisMapper.updateVerydissatisfied(id);
                    }

                } else {
                    if ("5".equals(ma.get("score").toString())) {
                        stDishesAnalysisMapper.updateDishevaluation(id);
                    } else if ("4".equals(ma.get("score").toString())) {
                        stDishesAnalysisMapper.updateSatisfied(id);
                    } else if ("3".equals(ma.get("score").toString())) {
                        stDishesAnalysisMapper.updateCommonly(id);
                    } else if ("2".equals(ma.get("score").toString())) {
                        stDishesAnalysisMapper.updateDissatisfied(id);
                    } else if ("1".equals(ma.get("score").toString())) {
                        stDishesAnalysisMapper.updateVerydissatisfied(id);
                    }
                }

            }
        }
    }

   /*
   用户评论回复
    */
    @Override
    public void replay(String replay,int id,String replaydate) {
        stDishesAnalysisMapper.replay(replay,id,replaydate);
    }

    /*
   修改用户评论
     */
    @Override
    public void updateReplay(String param) {
        JSONObject paramDate = JSONObject.parseObject(param);
        String replay = paramDate.getString("replay");
        int id = paramDate.getIntValue("id");
        int environment = paramDate.getIntValue("environment");
        int overallevaluation = paramDate.getIntValue("overallevaluation");
        int canteenservice = paramDate.getIntValue("canteenservice");
        stDishesAnalysisMapper.updateReplay(replay,id,environment,overallevaluation,canteenservice);
        //菜品信息
        List<Map> list = (List<Map>) paramDate.get("list");
        List imagesList = paramDate.getJSONArray("images");
        StringBuilder sb = new StringBuilder();
        for(Object image : imagesList){
            sb.append(image).append(",");
        }
        String images = sb.toString();
        stDishesAnalysisMapper.updateImages(id,images);
        for(Map li : list){
          Integer scoreid  = (Integer) li.get("id");
          Integer score = (Integer) li.get("score");
          stDishesAnalysisMapper.updateScore(scoreid,score);
        }
    }
    /*
    删除用户评论
     */
    @Override
    public void deleteReplay(int id) {
        stDishesAnalysisMapper.deleteReplayOfDis(id);
        stDishesAnalysisMapper.deleteReplay(id);
    }

    /*
    根据关键字筛选评论
     */
    @Override
    public List FilterComments(String param) {
        insertFilterComments(param);
        JSONObject Param = JSONObject.parseObject(param);
        String filtercomments = Param.getString("filtercomments");
        List list1 = new ArrayList();
       List<Map> list = stDishesAnalysisMapper.FilterComments(filtercomments);
       for(Map ma : list){
          list1.add(ma);
       }
       return list1;
    }

    /*
    获取排餐信息
     */
    @Override
    public List selectIntelligenceMeals(String param) {
        JSONObject paramDate = JSONObject.parseObject(param);
        String restaurant = paramDate.getString("restaurant");
        Date d = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(d);
        return stDishesAnalysisMapper.selectIntelligenceMeals(restaurant,date);
    }


    /*
    给菜品添加标签
     */
    public void insertFilterComments(String param) {
        JSONObject Param = JSONObject.parseObject(param);
        String strDate = Param.getString("strDate");
        String endDate = Param.getString("endDate");
        String restaurant = Param.getString("restaurant");
        String filtercomments1 = Param.getString("filtercomments");
        List<Map> list = stDishesAnalysisMapper.setAnalysisOfMyFoods(restaurant,strDate,endDate);
        switch (filtercomments1){
            case "1":
                for(Map ma : list){
                    if(ma.get("uploadpictures")==null){
                        String  filtercomments = "1";
                        stDishesAnalysisMapper.insertFilterComments((Integer) ma.get("id"),filtercomments);
                    }
        }
                break;
            case "2":
                for(Map ma : list){
                    if(ma.get("overallevaluation").toString().equals("4") || ma.get("overallevaluation").toString().equals("5")){
                        String  filtercomments = "2";
                        stDishesAnalysisMapper.insertFilterComments((Integer) ma.get("id"),filtercomments);
                    }
                }
           break;
            case "3":
                for(Map ma : list){
                    if(ma.get("overallevaluation").toString().equals("1") || ma.get("overallevaluation").toString().equals("2")){
                        String  filtercomments = "3";
                        stDishesAnalysisMapper.insertFilterComments((Integer) ma.get("id"),filtercomments);
                    }
                }
                break;
            case "4":
                for(Map ma : list){
                    if(ma.get("environment").toString().equals("4") || ma.get("environment").toString().equals("5")){
                        String  filtercomments = "4";
                        stDishesAnalysisMapper.insertFilterComments((Integer) ma.get("id"),filtercomments);
                    }
                }
                break;

            case "5":
                for(Map ma : list){
                    if(ma.get("environment").toString().equals("1") || ma.get("environment").toString().equals("2")){
                        String  filtercomments = "5";
                        stDishesAnalysisMapper.insertFilterComments((Integer) ma.get("id"),filtercomments);
                    }
                }
                break;

            case "6":
                for(Map ma : list){
                    if(ma.get("canteenservice").toString().equals("4") || ma.get("canteenservice").toString().equals("5")){
                        String  filtercomments = "6";
                        stDishesAnalysisMapper.insertFilterComments((Integer) ma.get("id"),filtercomments);
                    }
                }
                break;

            case "7":
                for(Map ma : list){
                    if(ma.get("canteenservice").toString().equals("1") || ma.get("canteenservice").toString().equals("2")){
                        String  filtercomments = "7";
                        stDishesAnalysisMapper.insertFilterComments((Integer) ma.get("id"),filtercomments);
                    }
                }
                break;

            case "8":
                for(Map ma : list){
                   List<Map> mapList = stDishesAnalysisMapper.selectDishesScore((Integer) ma.get("id"));
                     for(Map map : mapList){
                         if( map.get("score").toString().equals("4") ||  map.get("score").toString().equals("5")){
                             String  filtercomments = "8";
                             stDishesAnalysisMapper.insertFilterComments((Integer) ma.get("id"),filtercomments);
                         }

                     }

                }
                break;
            case "9":
                for(Map ma : list){
                    List<Map> mapList = stDishesAnalysisMapper.selectDishesScore((Integer) ma.get("id"));
                    for(Map map : mapList){
                        if( map.get("score").toString().equals("1") ||  map.get("score").toString().equals("52")){
                            String  filtercomments = "9";
                            stDishesAnalysisMapper.insertFilterComments((Integer) ma.get("id"),filtercomments);
                    }
                }
                    break;
        }

    }

}
    /*
    用户评价查询
     */
    @Override
    public PageBean selectUserComment(String param){
        JSONObject Param = JSONObject.parseObject(param);
        String strDate = Param.getString("strDate");
        String endDate = Param.getString("endDate");
        String restaurant = Param.getString("restaurant");
        String name = Param.getString("name");
        int pageNum = Integer.parseInt(Param.getString("pageNum"));
        int pageSize = Integer.parseInt(Param.getString("pageSize"));
        String filtercomments = Param.getString("filtercomments");
        if(filtercomments.length()==1){
            insertFilterComments(param);
        }
        List Dislist  = new ArrayList();
        int totalSize =stDishesAnalysisMapper.getTotals(restaurant,strDate,endDate,name,filtercomments);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if( totalSize == 0 ){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List<Map> equipmentList = stDishesAnalysisMapper.selectAnalysisOfMyFoods(restaurant,strDate,endDate,name,filtercomments,startIndex,pageSize);
            for(Map map1 : equipmentList){
                Map DisMap = new HashMap();
                String uploadpictures = (String) map1.get("uploadpictures");
                if(uploadpictures != null){
                    String[] strs = uploadpictures.split(",");
                    DisMap.put("uploadpictures",strs);
                }else {
                    int[] st = new int[0];
                    DisMap.put("uploadpictures",st);
                }
                DisMap.put("replay",stDishesAnalysisMapper.selectReplay((Integer) map1.get("id")));
                DisMap.put("date1",map1);
                DisMap.put("date",stDishesAnalysisMapper.selectDishesScore((Integer) map1.get("id")));
                Dislist.add(DisMap);
            }
            pageBean.setList(Dislist);
        }
        return pageBean;
    }


   /*
   用户发表评论
    */
    @Override
    public void postComment(String param){
        JSONObject paramDate = JSONObject.parseObject(param);
        List files =paramDate.getJSONArray("files");
      List list = FileUpload.UploadFile(files);
        String uploadpictures  = null;
        StringBuilder sb = new StringBuilder();
        if(list.size() != 0){
            for(Object image : list){
                sb.append(image).append(",");
            }
            sb.deleteCharAt(sb.length()-1);
            uploadpictures = sb.toString();
        }else {
            uploadpictures = null;
        }

        String content = paramDate.getString("content");
        int environment = paramDate.getIntValue("environment");
        int overallevaluation = paramDate.getIntValue("overallevaluation");
        int canteenservice = paramDate.getIntValue("canteenservice");
        String user = paramDate.getString("user");
      String stDishevaluation = paramDate.getString("dishevaluation");
      String begintime = paramDate.getString("begintime");
        StDishevaluation dishevaluation = new StDishevaluation(0,environment,overallevaluation,canteenservice,
                content,uploadpictures,user,stDishevaluation,"0","0");
        stDishesAnalysisMapper.saveComment(dishevaluation);
        int id = dishevaluation.getId();
       List<Map> listScore = (List<Map>)paramDate.get("list");
        for(Map ma : listScore){
         String  sh = ma.toString();
            Integer disid = Integer.parseInt(ma.get("id").toString());
            Integer score = Integer.parseInt(ma.get("score").toString());
            String name = ma.get("name").toString();
           stDishesAnalysisMapper.saveScore(id,score,name,disid);
        }

    }
}
