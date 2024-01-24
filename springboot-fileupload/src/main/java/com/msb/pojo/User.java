package com.msb.pojo;

import lombok.Data;

@Data
public class User {
    private String id;
    private String loginName;
    private String loginPassword;
    private String name;
    private String sex;
    private String gsType;
}
