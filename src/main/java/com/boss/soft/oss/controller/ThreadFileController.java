package com.boss.soft.oss.controller;

import com.aliyun.oss.model.OSSObjectSummary;
import com.boss.soft.oss.entity.FileUploadResult;
import com.boss.soft.oss.service.ThreadFileService;
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
 * @date 2020/7/27 11:56
 */
@Controller
@RequestMapping("/thread")
public class ThreadFileController {
    
    ThreadFileService threadService;
    private static final Logger logger = LoggerFactory.getLogger(ThreadFileController.class);

    public ThreadFileController(ThreadFileService threadService) {
        this.threadService = threadService;
    }

    @RequestMapping("/upload")
    @ResponseBody
    public FileUploadResult upload(@RequestParam("file") MultipartFile uploadFile)
            throws Exception {
        logger.info("upload");
        return this.threadService.upload(uploadFile);
    }

    @RequestMapping("/list")
    @ResponseBody
    public List<OSSObjectSummary> list() throws Exception {
        logger.info("list");
        return this.threadService.list();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public FileUploadResult delete(@RequestParam("fileName") String objectName)
            throws Exception {
        logger.info("delete");
        return this.threadService.delete(objectName);
    }

    @RequestMapping("/download")
    @ResponseBody
    public void download(@RequestParam("fileName") String objectName, HttpServletResponse response) throws IOException {
        logger.info("download");
        response.setHeader("Content-Disposition",
                "attachment;filename=" + new String(objectName.getBytes(), "ISO-8859-1"));
        this.threadService.exportOssFile(response.getOutputStream(),objectName);
    }

}
