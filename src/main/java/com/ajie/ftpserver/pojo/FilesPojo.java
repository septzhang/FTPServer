package com.ajie.ftpserver.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * @ClassName Files
 * @Description
 * @Author septzhang
 * @Date 2022/2/12 18:53
 * @Version 1.0
 **/
@Data
@TableName("files")
public class FilesPojo {
    /**
     * 自增ID
     */
    @TableId(type = IdType.AUTO)
    private int fileId;
    /**
     * 文件大小
     */
    private Long fileSize;
    /**
     * 文件类型
     */
    private String fileType;
    /**
     * 原始文件名
     */
    private String fileOldName;
    /**
     * 创建时间戳
     */
    @TableField(fill = FieldFill.INSERT)
    private Date fileCreatetime;
    /**
     * 文件保存目录地址
     */
    private String filePath;
    /**
     * 文件保存UUID
     */
    private String fileUuid;
}
