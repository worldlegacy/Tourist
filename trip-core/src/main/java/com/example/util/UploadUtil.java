package com.example.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyuncs.exceptions.ClientException;
import org.apache.velocity.shaded.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @Author Artist
 * @Description 阿里云上传模板修改版本
 * @Date 2023/8/4
 */
public class UploadUtil {
    public static String uploadAli(MultipartFile pic) throws IOException{
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
        // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
//        EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "linshiwanjia";
        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
        /**
         * 设置自动生成的文件名
         */
        String uuid = UUID.randomUUID().toString();
        /**
         * 获取原文件名
         */
        String originalFilename = pic.getOriginalFilename();
        /**
         * 获取扩展名
         */
        String extension = FilenameUtils.getExtension(originalFilename);
        /**
         * 新文件名
         */
        String fileName = uuid + "." + extension;
        String objectName = fileName;
        // 填写网络流地址。
        String url = "https://www.aliyun.com/";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint,"LTAI5t5pD1nWUiE3JBchR5x2","t8XI5En4c54BDnDvpKdBJluqive1CF");

            InputStream inputStream = pic.getInputStream();
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);
            // 创建PutObject请求。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
        if (ossClient != null) {
            ossClient.shutdown();
        }
        /**
         * 返回到我的阿里云oss地址，返回的名字做拼接
         */
        return "https://linshiwanjia.oss-cn-beijing.aliyuncs.com/" + objectName;
    }
}
