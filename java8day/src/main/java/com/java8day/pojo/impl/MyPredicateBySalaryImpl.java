package com.java8day.pojo.impl;

import com.java8day.pojo.Employee;
import com.java8day.pojo.MyPredicate;

public class MyPredicateBySalaryImpl implements MyPredicate<Employee> {
    @Override
    public boolean test(Employee employee) {
        return employee.getSalary() >777;
    }
}
