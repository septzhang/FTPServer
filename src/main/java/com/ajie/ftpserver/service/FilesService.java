package com.ajie.ftpserver.service;

import com.ajie.ftpserver.pojo.FilesPojo;
import com.ajie.ftpserver.utils.R;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


/**
 * @ClassName FilesService
 * @Description
 * @Author septzhang
 * @Date 2022/2/12 19:09
 * @Version 1.0
 **/
public interface FilesService extends IService<FilesPojo> {

    R saveFile(MultipartFile file);

    R downloadFile(String file);

    R getInfoFile(String fileUUID);
}
