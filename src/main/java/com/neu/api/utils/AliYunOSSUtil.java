package com.neu.api.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.ByteArrayInputStream;

@Data
@AllArgsConstructor
public class AliYunOSSUtil {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;

    private String  bucketName;

    /**
     * 文件上传
     *
     * @param bytes       文件的内容
     * @param objectName  文件的名称
     * @return
     */
    public String upload(byte[] bytes, String objectName) {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 创建PutObject请求。
            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(bytes));
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS,but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered a serious internal problem while trying to communicate with OSS,such as not being able to access the network.");
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        StringBuilder stringBuilder = new StringBuilder("https://");
        stringBuilder.append(bucketName).append(".").append(endpoint).append("/").append(objectName);
        return stringBuilder.toString();
    }
}
