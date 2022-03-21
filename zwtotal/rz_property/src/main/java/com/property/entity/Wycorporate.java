package com.property.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Wycorporate {
    private Integer id;

    private String parentIds;

    private String parentId;

    private String corporatename;

    List<Wycorporate> Subclass  = new ArrayList<>();
}