package com.msb.utils;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.MinioException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class FileUploader {
    public static void main(String[] args)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            // Create a minioClient with the MinIO server playground, its access key and secret key.
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint("http://192.168.56.10:9000")
                            .credentials("zpp", "12345678")
                            .build();

            // 创建bucket Make 'asiatrip' bucket if not exist.
            String buckerName="testtuling";
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(buckerName).build());
            if (!found) {
                //不存在创建桶 Make a new bucket called 'asiatrip'.
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(buckerName).build());
            } else {
                System.out.println("Bucket 'testtuling' already exists.");
            }

            // Upload '/home/user/Photos/asiaphotos.zip' as object name 'asiaphotos-2015.zip' to bucket
            //上传 'asiatrip'.
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            //指定桶的名称
                            .bucket(buckerName)
                            //指定上传到minio文件的名称
                            .object("pan.jpg")
                            //本地磁盘的地址
                            .filename("D:/cssc/pan.jpg")
                            .build());
            System.out.println(
                    "'/home/user/Photos/asiaphotos.zip' is successfully uploaded as "
                            + "object 'asiaphotos-2015.zip' to bucket 'asiatrip'.");
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
            System.out.println("HTTP trace: " + e.httpTrace());
        }
    }
}
