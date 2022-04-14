package com.mstdemo.mst.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Permission {
    private String id;
    private String name;
    private String pid;
    private String url;
    private boolean open = true;
    private List<Permission> children =new ArrayList<>();
}
