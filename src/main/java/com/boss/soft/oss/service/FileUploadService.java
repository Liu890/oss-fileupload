package com.boss.soft.oss.service;

import com.boss.soft.oss.entity.FileUploadResult;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author ljx
 * @date 2020/7/23 14:39
 */
@Component
public interface FileUploadService {

    /**
     * 文件上传
     *
     * @param uploadFile
     * @return Result<FileUploadResult>
     */
    FileUploadResult upload(MultipartFile uploadFile);

    /**
     * 删除文件
     *
     * @param objectName
     * @return Result<FileUploadResult>
     */
    FileUploadResult delete(String objectName);

    /**
     * 下载文件
     *
     * @param os
     * @param objectName
     * @throws  IOException
     * @return
     */
    void exportOssFile(OutputStream os, String objectName) throws IOException;

}
