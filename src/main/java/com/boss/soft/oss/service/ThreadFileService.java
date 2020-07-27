package com.boss.soft.oss.service;

import com.aliyun.oss.model.OSSObjectSummary;
import com.boss.soft.oss.entity.FileUploadResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author ljx
 * @date 2020/7/27 11:41
 */
@Component
public interface ThreadFileService {
    /**
     * 多线程文件上传
     *
     * @param uploadFile
     * @return Result<FileUploadResult>
     */
    @Async
    FileUploadResult upload(MultipartFile uploadFile);

    /**
     * 多线程下载文件
     *
     * @param os
     * @param objectName
     * @return
     * @throws IOException
     */
     void exportOssFile(OutputStream os, String objectName) throws IOException;

     /**
     * 删除文件
     *
     * @param objectName
     * @return Result<FileUploadResult>
     */
     @Async
    FileUploadResult delete(String objectName);

     /**
     * 多线程显示图片
     *
     * @param
     * @return Result<List<OSSObjectSummary>>
     */
    List<OSSObjectSummary> list();
}
