package com.atguigu.mybatisplus;
import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class MybatisPlusWrapperTest {
    @Autowired
   private UserMapper userMapper;

    @Test
    public void test01(){
        //查询用户包含a，年龄在20到30岁之间，邮箱信息不为null的用户信息
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("user_name","张")
        .between("age",10,30).
        isNotNull("emial");
        List<User> userList= userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);

    }
    @Test
    public void test02(){
        //查询用户信息，按照年龄降序，若年龄相同按照id升序
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("age").orderByAsc("uid");
        List<User> userlist = userMapper.selectList(queryWrapper);
        userlist.forEach(System.out::println);

    }
    @Test
    public void test03(){
        //删除字段为空的数据
       QueryWrapper<User> queryWrapper =  new QueryWrapper<>();
       queryWrapper.isNull("emial");
       int result = userMapper.delete(queryWrapper);
        System.out.println("result:===" + result);

    }
    @Test
    public void test04(){
        //将年龄大于20，并且用户中包含有张 或邮箱为null的用户信息修改
       QueryWrapper<User> queryWrapper =  new QueryWrapper<>();
       queryWrapper.gt("age",20).like("user_name","张").or().isNull("emial");
       User user=new User();
       user.setName("盼盼");
       user.setAge(32);
        int result = userMapper.update(user,queryWrapper);
        System.out.println("result:" + result);

    }

    @Test
    public void test05(){
    //将用户名中包含有a并且（年龄大于20或者邮箱为null）用户信息修改
    //lambda中的条件优先执行
    //UPDATE t_user SET user_name=?, age=? WHERE is_del='0' AND (user_name LIKE ? AND (age > ? OR emial IS NULL))
   QueryWrapper<User> queryWrapper= new QueryWrapper<>();
   User user=new User();
   user.setName("小红");
   user.setAge(26);
   queryWrapper.like("user_name","张").and( i->i.gt("age",20).or().isNull("emial"));
    int result = userMapper.update(user,queryWrapper);
    System.out.println("result:===" + result);

    }
    @Test
    public void test06(){
        //查询指定的字段
       QueryWrapper<User> queryWrapper=new QueryWrapper<>();
       queryWrapper.select("user_name","age");
        List<Map<String,Object>> userList = userMapper.selectMaps(queryWrapper);
        userList.forEach(System.out::println);
    }
    @Test
    public void test07(){
        //子查询:SELECT uid,user_name AS name,age,emial,is_del FROM t_user WHERE is_del='0' AND (uid IN (select uid from t_user<=20))
        QueryWrapper<User> queryWrapper =new QueryWrapper<>();
        queryWrapper.inSql("uid","select uid from t_user<=20");
        List<User> userList= userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }
    @Test
    public void test08(){
        //将用户名中包含有a并且（年龄大于20或者邮箱为null）用户信息修改
        UpdateWrapper<User> updateWrapper=new UpdateWrapper<>();
        updateWrapper.like("user_name","张").and(i->i.gt("age",20).or().isNull("emial"));
        updateWrapper.set("user_name","张三").set("age","30");
       int result = userMapper.update(null,updateWrapper);
        System.out.println("result:===》"+result);

    }

    /**
     * 条件判断查询
     * SELECT uid,user_name AS name,age,emial,is_del FROM t_user WHERE is_del='0' AND (age > ? AND age < ?)
     */
    @Test
    public void test09(){
        String userName="";
        Integer ageBegin=20;
        Integer ageEnd=30;
        QueryWrapper<User>  queryWrapper =new QueryWrapper<>();
        //isNotBlank判断某个字符是否不为空字符串
        if(StringUtils.isNotBlank(userName)){
            queryWrapper.like("user_name","张");
        }
        if(ageBegin !=null){
            queryWrapper.gt("age",10);
        }
        if(ageEnd != null){
            queryWrapper.lt("age",20);
        }
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * mybatis-plus:改善条件判断查询
     */
    @Test
    public void test10(){
        String userName="";
        Integer ageBegin=20;
        Integer ageEnd=30;
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(userName),"user_name",userName);
        queryWrapper.gt(ageBegin !=null,"age",ageBegin);
        queryWrapper.lt(ageEnd !=null,"age",ageEnd);
        List<User>  userList= userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }
    /**
     * lamda表达式 ：改善条件判断查询
     * SELECT uid,user_name AS name,age,emial,is_del FROM t_user WHERE is_del='0' AND (age >= ? AND age <= ?)
     */
    @Test
    public void test11(){
        String userName="";
        Integer ageBegin=20;
        Integer ageEnd=30;
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotBlank(userName),User::getName,userName);
        lambdaQueryWrapper.ge(ageBegin !=null,User::getAge,userName);
        lambdaQueryWrapper.le(ageEnd !=null,User::getAge,ageEnd);
        List<User> userList = userMapper.selectList(lambdaQueryWrapper);
        userList.forEach(System.out::println);
    }
    /**
     * lamda表达式 ：修改
     *  UPDATE t_user SET user_name=?,age=? WHERE is_del='0' AND (user_name LIKE ? AND (age > ? OR emial IS NULL))
     */
    @Test
    public void test12(){
        //将用户名中包含有a并且（年龄大于20或者邮箱为null）用户信息修改
        LambdaUpdateWrapper<User> lambdaUpdateWrapper=new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.like(User::getName,"张").and(i->i.gt(User::getAge,20).or().isNull(User::getEmial));
        lambdaUpdateWrapper.set(User::getName,"张三").set(User::getAge,"30");
        int result = userMapper.update(null,lambdaUpdateWrapper);
        System.out.println("result:===》"+result);
    }






}
