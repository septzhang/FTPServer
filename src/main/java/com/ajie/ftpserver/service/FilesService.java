package com.ajie.ftpserver.service;

import com.ajie.ftpserver.pojo.FilesPojo;
import com.ajie.ftpserver.utils.R;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;


/**
 * @ClassName FilesService
 * @Description
 * @Author septzhang
 * @Date 2022/2/12 19:09
 * @Version 1.0
 **/

public interface FilesService extends IService<FilesPojo> {

    /**
     * 存储File
     * @param file 文件
     * @return
     */
    R saveFile(MultipartFile file);

    /**
     * 下载文件
     * @param fileUUID 文件的UUID
     * @return FilesPojo对象
     */
    FilesPojo downloadFile(String fileUUID);

    /**
     * 查询文件
     * @param fileUUID 文件的UUID
     * @return
     */
    R getInfoFile(String fileUUID);
}
