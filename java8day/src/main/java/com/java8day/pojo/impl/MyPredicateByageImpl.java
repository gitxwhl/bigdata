package com.java8day.pojo.impl;

import com.java8day.pojo.Employee;
import com.java8day.pojo.MyPredicate;

public class MyPredicateByageImpl implements MyPredicate<Employee> {

    @Override
    public boolean test(Employee t) {
        return t.getAge() > 40;
    }
}
