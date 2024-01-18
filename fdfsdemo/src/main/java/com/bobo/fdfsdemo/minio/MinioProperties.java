package com.bobo.fdfsdemo.minio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {
    String endpoint;
    String accessKey;
    String secretKey;

}
