//package com.canteen.bootstrap.config;
//
//import io.swagger.models.Contact;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.StringHttpMessageConverter;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import java.nio.charset.Charset;
//import java.util.List;
//
///**
// * @ClassName SwaggerConfig
// * @Date 2020/9/1
// * @Auther tiandejiang
// * @Description: TODO
// * @Vserion 1.0.0
// */
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig extends WebMvcConfigurationSupport {
//    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())//调用apiInfo方法,创建一个ApiInfo实例,里面是展示在文档页面信息内容
//                .enable(true)//开启swagger
//                .select()
//                //控制暴露出去的路径下的实例
//                //如果某个接口不想暴露,可以使用以下注解
//                //@ApiIgnore 这样,该接口就不会暴露在 swagger2 的页面下
//                //.apis(RequestHandlerSelectors.basePackage("com.study.controller"))
////                .apis(RequestHandlerSelectors.withClassAnnotation(ApiOperation.class))//使用注解扫描失败
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
////        registry.addResourceHandler("/**")
////                .addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//        super.addResourceHandlers(registry);
//    }
//
//    //构建 api文档的详细信息函数
//    private ApiInfo apiInfo() {
//        //Contact contact = new Contact("tdj","localhost:8080","12312312321");
//        return new ApiInfoBuilder()
//                //页面标题
//                .title("河北智慧后期")
//                //条款地址
//                .termsOfServiceUrl("")
//                //.contact(contact)
//                .version("1.0")
//                //描述
//                .description("API 描述")
//                .build();
//    }
//
//    //解决中文乱码
//    @Bean
//    public HttpMessageConverter<String> responseBodyConverter() {
//        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
//        return converter;
//    }
//
//    @Override
//    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        super.configureMessageConverters(converters);
//        converters.add(responseBodyConverter());
//    }
//}
