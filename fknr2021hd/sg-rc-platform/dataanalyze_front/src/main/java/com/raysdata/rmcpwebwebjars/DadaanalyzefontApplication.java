package com.raysdata.rmcpwebwebjars;

import com.nariit.rmcp.common.vo.WrappedResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;

//@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {"com.nariit.rmcp"})
@EnableCaching
@Controller
@Configuration
@RefreshScope
@ComponentScan(
        basePackages = {"com.raysdata.rmcpwebwebjars", "com.nariit.rmcp", "com.nariit.pi6000", "com.nariit.adf"}
)
public class DadaanalyzefontApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DadaanalyzefontApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        builder.sources(new Class[]{DadaanalyzefontApplication.class});
        return super.configure(builder);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

//    /**
//     *  aqzrUrl安全准人地址
//     */
//    @Value("${aqzrUrl}")
//    private String aqzrUrl;
//
//    /**
//     *  dwfxUrl电网风险地址
//     */
//    @Value("${dwfxUrl}")
//    private String dwfxUrl;

//    /**
//     *  作业模块地址
//     */
//    @Value("${gatewayURL2}")
//    private String gatewayURL2;
//
//    /**
//     *  getway网关地址
//     */
//    @Value("${gatewayURL}")
//    private String gatewayURL;
//
////    /**
////     *  loginUrl登录地址
////     */
////    @Value("${loginUrl}")
////    private String loginUrl;
//
////    /**
////     *  tool工器具服务地址
////     */
////    @Value("${gqjUrl}")
////    private String gqjUrl;
//
//
////    /**
////     *  导入导出服务地址
////     */
////    @Value("${exportServiceUrl}")
////    private String exportServiceUrl;
////
////    /**
////     *  工器具服务地址
////     */
////    @Value("${toolsServiceUrl}")
////    private String toolsServiceUrl;
//
   /**
     * ISC登录地址
     */
    @Value("${isc.sso}")
    private String iscSso;
//
//    /**
//     * 业务系统根地址
//     */
    @Value("${isc.busiUrl}")
    private String busiUrl;
//
////    /**
////     * 安全风险全景感知
////     */
    @Value("${gatewayURL}")
    private String gatewayURL;

////    /**
////     * 安全生产大数据分析
////     */
////    @Value("${dsjfxUrl:http://127.0.0.1:10040/risk-dataanalyze-server/index.html}")
////    private String dsjfxUrl;
////
    /**
     *  get 获取网关地址配置
     *
     * @return
     */
    @RequestMapping("/getUrl")
    @ResponseBody
    public WrappedResult getGatewayUrl() {
        Map<String, Object> result = new HashMap<>();
//        result.put("aqzrUrl", aqzrUrl);
 //       result.put("dwfxUrl", dwfxUrl);
          result.put("gatewayURL", gatewayURL);
//        result.put("gatewayURL2", gatewayURL2);
 //       result.put("loginUrl", loginUrl);
//        result.put("gqjUrl", gqjUrl);
 //       result.put("aqfxgzUrl", aqfxgzUrl);
  //      result.put("dsjfxUrl", dsjfxUrl);
  //      result.put("exportServiceUrl", exportServiceUrl);
  //      result.put("toolsServiceUrl", toolsServiceUrl);
        result.put("iscSso", iscSso);
        result.put("busiUrl", busiUrl);
        return WrappedResult.successWrapedResult(result);
    }
}
