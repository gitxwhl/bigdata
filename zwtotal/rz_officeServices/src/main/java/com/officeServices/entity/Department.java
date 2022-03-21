package com.officeServices.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    private String id;

    private String parentId;

    private String name;

    private List<Department> subClass = new ArrayList<>();
}
