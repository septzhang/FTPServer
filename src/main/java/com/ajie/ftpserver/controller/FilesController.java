package com.ajie.ftpserver.controller;

import com.ajie.ftpserver.pojo.FilesPojo;
import com.ajie.ftpserver.service.FilesService;
import com.ajie.ftpserver.utils.R;
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
            /**
             * 设置文件名
             */
            String fileName = filesPojo.getFileOldName();
            if (fileName != null) {
                /**
                 * 设置文件路径
                 */
                File file = new File(filesPojo.getFilePath());
                if (file.exists()) {
                    /**
                     * 设置强制下载不打开
                     */
                    response.setContentType("application/force-download");
                    /**
                     * 设置文件名
                     */
                    response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
                    byte[] buffer = new byte[1024];
                    FileInputStream fis = null;
                    BufferedInputStream bis = null;
                    try {
                        fis = new FileInputStream(file);
                        bis = new BufferedInputStream(fis);
                        OutputStream os = response.getOutputStream();
                        int i = bis.read(buffer);
                        while (i != -1) {
                            os.write(buffer, 0, i);
                            i = bis.read(buffer);
                        }
                        return R.ok("下载成功"+file.getName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (bis != null) {
                            try {
                                bis.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (fis != null) {
                            try {
                                fis.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
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

