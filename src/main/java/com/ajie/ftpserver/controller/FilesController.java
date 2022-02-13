package com.ajie.ftpserver.controller;

import com.ajie.ftpserver.pojo.FilesPojo;
import com.ajie.ftpserver.service.FilesService;
import com.ajie.ftpserver.utils.R;
import com.ajie.ftpserver.utils.ResponseFileStreamUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @ClassName FileController
 * @Description
 * @Author septzhang
 * @Date 2022/2/12 19:06
 * @Version 1.0
 **/
@RestController
public class FilesController {
    @Autowired
    FilesService filesService;

    /**
     * 上传文件的接口
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file){
        return filesService.saveFile(file);
    }

    /**
     * 下载文件
     * @param fileUUID 文件的UUID
     * @return
     */
    @GetMapping("/download")
    public R download(@RequestParam("fileUUID") String fileUUID, HttpServletResponse response){
        FilesPojo filesPojo =filesService.downloadFile(fileUUID);
        if (null != filesPojo){
            ResponseFileStreamUtils.responseFileStream(response,filesPojo.getFilePath());
            R.ok("文件下载成功！").put("file",filesPojo);
        }
        return R.error(410,"异常响应");

    }


    /**
     * 查询文件信息
     * @param fileUUID
     * @return
     */
    @GetMapping("/getInfo")
    public R getInfo(@RequestParam("fileUUID") String fileUUID){
        return filesService.getInfoFile(fileUUID);
    }

}

