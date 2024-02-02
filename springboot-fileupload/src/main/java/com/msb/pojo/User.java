package com.msb.pojo;

import com.msb.anno.ExceelAttr;
import lombok.Data;

@Data
public class User {
    @ExceelAttr(sort = 0)
    private String loginName;
    @ExceelAttr(sort = 1)
    private String loginPassword;
    @ExceelAttr(sort =2)
    private String loginDate;
    @ExceelAttr(sort = 3)
    private String name;
    @ExceelAttr(sort = 4)
    private String gsType;
    @ExceelAttr(sort = 5)
    private String phone;
    private String id;
    private String sex;






}
