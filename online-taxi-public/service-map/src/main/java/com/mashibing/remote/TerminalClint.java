package com.mashibing.remote;

import com.mashibing.internalcommon.constent.AmapConfigConstents;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.ServiceRespose;
import com.mashibing.internalcommon.response.TerminalResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TerminalClint {

    @Value("${key.amap}")
    private String amapkey;
    @Value("${key.sid}")
    private String sid;

    @Autowired
    private RestTemplate restTemplate;

    //创建终端
    public ResponseResult add(String name) {
        //组装请求url
        StringBuilder builder = new StringBuilder();
        builder.append(AmapConfigConstents.TERMINAL_URL);
        builder.append("?");
        builder.append("key=" + amapkey);
        builder.append("&");
        builder.append("sid=" + sid);
        builder.append("&");
        builder.append("name=" + name);
        ResponseEntity<String> forEntity = restTemplate.postForEntity(builder.toString(), null, String.class);
        String body = forEntity.getBody();
        JSONObject result = JSONObject.fromObject(body);
        JSONObject data = result.getJSONObject("data");
        String tid = data.getString("tid");
        TerminalResponse terminalResponse = new TerminalResponse();
        terminalResponse.setTid(tid);
        return ResponseResult.success(terminalResponse);
    }


}
