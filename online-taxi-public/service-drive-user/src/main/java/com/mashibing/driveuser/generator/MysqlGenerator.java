package com.mashibing.driveuser.generator;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import java.util.Collections;
/**
 * 自动生成代码工具类
 */
public class MysqlGenerator {
    public static void main(String[] args) {

        FastAutoGenerator.create("jdbc:mysql://localhost:3306/service-drive-user?characterEncoding=utf-8&serverTimezone=GMT%2B8",
                "root","zpproot")
                .globalConfig(builder -> {
                    //设置输出的目录
                    builder.author("111").fileOverride().outputDir("D:\\github\\bigdata\\online-taxi-public\\service-drive-user\\src\\main\\java");
                })
                .packageConfig(builder -> {
                    //设置包的属性  parent 当前类MysqlGenerator所在的父包，
                    builder.parent("com.mashibing.driveuser").pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                            "D:\\github\\bigdata\\online-taxi-public\\service-drive-user\\src\\main\\java\\com\\mashibing\\driveuser\\mapper"));
                })
                //生成策略
                .strategyConfig(builder -> {
                    //生成的表名
                    builder.addInclude("driver_car_binding_relationship")
                    //过滤表的前缀或者后缀
//                    .addTablePrefix()
//                    .addTableSuffix()
                    ;
                    //
                })
                //模板引擎
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
