package com.msb.utils;

import io.minio.DownloadObjectArgs;
import io.minio.MinioClient;

public class DownLoadDemo {

    public static void main(String[] args) {
// Create a minioClient with the MinIO server playground, its access key
                MinioClient minioClient =
                MinioClient.builder()
                        .endpoint("http://192.168.56.10:9000")
                        .credentials("zpp", "12345678")
                        .build();
// Download object given the bucket, object name and output file name
        try {
            minioClient.downloadObject(
                    DownloadObjectArgs.builder()
                            .bucket("testtuling")
                            .object("pan.jpg")
                            .filename("D:/download/pan.jpg")
                            .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
