package com.canteen.utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private Object msg;
    private String code;//200代表用户不存在，401代表用户已存在
}
