package com.boss.soft.oss.entity;

import lombok.Data;

/**
 * @author ljx
 * @date 2020/7/23 10:59
 */
@Data
public class FileUploadResult {
    private String id;
    private String name;
    /**
     * 状态名
     */
    private  String status;
    /**
     * 服务端响应，如’{“status“：”success“}‘
     */
    private String response;
}
