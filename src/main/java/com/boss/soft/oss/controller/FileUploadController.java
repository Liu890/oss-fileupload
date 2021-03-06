package com.boss.soft.oss.controller;

import com.aliyun.oss.model.OSSObjectSummary;
import com.boss.soft.oss.service.impl.FileUploadServiceImpl;
import com.boss.soft.oss.entity.FileUploadResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author ljx
 * @date 2020/7/23 10:34
 */
@Controller
@RequestMapping("/file")
public class FileUploadController {

    private FileUploadServiceImpl fileUploadService;
    private static final Logger logger = LoggerFactory.getLogger(ThreadFileController.class);

    public FileUploadController(FileUploadServiceImpl fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @RequestMapping("/upload")
    @ResponseBody
    public FileUploadResult upload(@RequestParam("file") MultipartFile uploadFile)
            throws Exception {
        return this.fileUploadService.upload(uploadFile);
    }

    @RequestMapping("/list")
    @ResponseBody
    public List<OSSObjectSummary> list() throws Exception {
        return this.fileUploadService.list();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public FileUploadResult delete(@RequestParam("fileName") String objectName)
            throws Exception {
        return this.fileUploadService.delete(objectName);
    }

    @RequestMapping("/download")
    @ResponseBody
    public void download(@RequestParam("fileName") String objectName, HttpServletResponse response) throws IOException {
        logger.info("down");
        response.setHeader("Content-Disposition",
                "attachment;filename=" + new String(objectName.getBytes(), "ISO-8859-1"));
        this.fileUploadService.exportOssFile(response.getOutputStream(),objectName);
    }
}