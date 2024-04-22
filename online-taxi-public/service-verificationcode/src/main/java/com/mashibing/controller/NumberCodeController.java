package com.mashibing.controller;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberCodeController {
    @GetMapping("/numberCode/{size}")
    public ResponseResult<NumberCodeResponse> getNumberCode(@PathVariable("size") int size){
        //随机生成验证码:random随机生成的是0.xxxxxx  乘9之后如果首位不是0的话是整数.xxxxxx
        //如果是0的话需要加1保证整数为不为0，然后再乘以10整数次方往后移动小数点，输入的值和想要的
        //位数相差1，所以size需要减1
        double resultInt= (Math.random()*9+1)*Math.pow(10,size-1);
        //返回视图
        NumberCodeResponse response=new NumberCodeResponse();
        response.setNumberCode((int) resultInt);
        System.out.println("生成验证码服务============="+(int) resultInt);
       /* JSONObject json =new JSONObject();
        json.put("code",1);
        json.put("message","success");
        JSONObject data =new JSONObject();
        data.put("numberCode",123456);
        json.put("data",(int)resultInt);*/

        return ResponseResult.success(response);
    }




}
