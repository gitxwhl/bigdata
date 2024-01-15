package com.bobo.fdfsdemo;

import com.luhuiguo.fastdfs.domain.StorePath;
import com.luhuiguo.fastdfs.service.FastFileStorageClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;

@SpringBootTest
class FdfsdemoApplicationTests {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Test
    void contextLoads() throws Exception {
       File file = new File("D:\\cssc\\pan.jpg");
        StorePath path =  fastFileStorageClient.uploadFile("",new FileInputStream(file),file.length(),"jpg");
        System.out.println("path.getFullPath" + path.getFullPath());
    }

}
