package com.java8day.pojo;
import com.java8day.pojo.impl.MyPredicateBySalaryImpl;
import com.java8day.pojo.impl.MyPredicateByageImpl;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestLambda {
    List<Employee> emps= Arrays.asList(
            new Employee(101,"张三",18,999.9),
            new Employee(102,"李四",59,666.6),
            new Employee(103,"王五",28,333.33),
            new Employee(104,"赵六",8,777.7),
            new Employee(105,"田七",38,555.5)
    );

    @Test
    public void test1(){
        List<Employee> em = fileEmployees(emps);
        for (Employee employee: em){
            System.out.println(employee);
        }


//        --------------------------------------






//        for (Employee s :em){
//            System.out.println(s);
//        }
    }


//  ----------------------------------分别筛选的话需要两个方法---------------------------------------------
    //获取公司员工年龄大于35的员工
    public List<Employee> fileEmployees(List<Employee> ems){
        List<Employee> list =new ArrayList();
        for (Employee e:ems){
            if(e.getAge()> 35){
                list.add(e);
            }
        }
        return list;
    }

    //获取公司员工工资大于5000的员工
    public List<Employee> fileEmployeegz(List<Employee> ems){
        List<Employee> list =new ArrayList();
        for (Employee e:ems){
            if(e.getSalary()> 5000){
                list.add(e);
            }
        }
        return list;
    }

//    优化方式1----------------------------------策略模式---------------------------------------------
        @Test
        public void test2(){
//        按照年龄筛选
            List<Employee> li = fileEmployeecl(emps,new MyPredicateByageImpl());
            for (Employee ems: li){
                System.out.println("--------age--------->" + ems);
            }

// ------------------------------------------------------------------------------------
//            按照工资筛选
            List<Employee> lisa =  fileEmployeecl(emps,new MyPredicateBySalaryImpl());
            for (Employee ee:lisa){
                System.out.println("---------工资-------->" + ee);
            }
            }

        public List<Employee> fileEmployeecl(List<Employee> list,MyPredicate<Employee> myPredicate){
            List<Employee> el =new ArrayList<>();
            for (Employee ey :list){
                if(myPredicate.test(ey)){
                    el.add(ey);
                }
            }
            return el;
        }

//    优化方式2:匿名内部类-------------------------------------------------------------
    @Test
    public void test3(){
        List<Employee>  ep= fileEmployeecl(emps, new MyPredicate<Employee>() {
            @Override
            public boolean test(Employee es) {
                return es.getSalary()<= 500;
            }
        });
        for (Employee me:ep){
                System.out.println(me);
        }
    }

//    优化方式3：lambda表达式
    @Test
    public void test4(){
        List<Employee> listoy = fileEmployeecl(emps , (e) -> e.getSalary()>500);
        listoy.forEach(System.out::println);
    }

//    优化方式4：Stream API






}
