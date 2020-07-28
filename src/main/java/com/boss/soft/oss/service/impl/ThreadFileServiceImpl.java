package com.boss.soft.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.boss.soft.oss.config.AliyunConfig;
import com.boss.soft.oss.entity.FileUploadResult;
import com.boss.soft.oss.service.ThreadFileService;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * @author ljx
 * @date 2020/7/27 11:41
 */
@Service
public class ThreadFileServiceImpl implements ThreadFileService {

    private static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg",
            ".jpeg", ".gif", ".png"};

    @Autowired
    private OSS ossClient;
    @Autowired
    private AliyunConfig aliyunConfig;

    @Override
    @Async
    public FileUploadResult upload(MultipartFile uploadFile) {
        boolean isLegal = false;
        for (String type : IMAGE_TYPE) {
            if (StringUtils.endsWithIgnoreCase(uploadFile.getOriginalFilename(), type)) {
                isLegal = true;
                break;
            }
        }
        FileUploadResult fileUploadResult = new FileUploadResult();
        if (!isLegal) {
            fileUploadResult.setStatus("error");
            return fileUploadResult;
        }
        String fileName = uploadFile.getOriginalFilename();
        String filePath = getFilePath(fileName);
        try {
            ossClient.putObject(aliyunConfig.getBucketName(), filePath, new
                    ByteArrayInputStream(uploadFile.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            fileUploadResult.setStatus("error");
            return fileUploadResult;
        }
        fileUploadResult.setStatus("done");
        fileUploadResult.setResponse("success");
        fileUploadResult.setName(this.aliyunConfig.getUrlPrefix() + filePath);
        fileUploadResult.setId(String.valueOf(System.currentTimeMillis()));
        return fileUploadResult;
    }

    @Override
    public void exportOssFile(OutputStream os, String objectName) throws IOException {
        BufferedInputStream in = new BufferedInputStream( ossClient.getObject(aliyunConfig.getBucketName(), objectName).getObjectContent());
        BufferedOutputStream out = new BufferedOutputStream(os);
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = in.read(buffer)) != -1) {
            out.write(buffer, 0, length);
        }
        if (out != null) {
            out.flush();
            out.close();
        }
        if (in != null) {
            in.close();
        }
    }

    @Override
    @Async
    public FileUploadResult delete(String objectName) {
        ossClient.deleteObject(aliyunConfig.getBucketName(), objectName);
        FileUploadResult fileUploadResult = new FileUploadResult();
        fileUploadResult.setName(objectName);
        fileUploadResult.setStatus("removed");
        fileUploadResult.setResponse("success");
        return fileUploadResult;
    }

    @Override
    public List<OSSObjectSummary> list() {
        final int maxKeys = 200;
        ObjectListing objectListing = ossClient.listObjects(new ListObjectsRequest(aliyunConfig.getBucketName()).withMaxKeys(maxKeys));
        List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
        return sums;
    }

    private String getFilePath(String sourceFileName) {
        DateTime dateTime = new DateTime();
        return "images/" + dateTime.toString("yyyy")
                + "/" + dateTime.toString("MM") + "/"
                + dateTime.toString("dd") + "/" + System.currentTimeMillis() +
                RandomUtils.nextInt(100, 9999) + "." +
                StringUtils.substringAfterLast(sourceFileName, ".");
    }

}
