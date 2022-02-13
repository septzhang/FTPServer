package com.ajie.ftpserver.controller;

import com.ajie.ftpserver.pojo.FilesPojo;
import com.ajie.ftpserver.service.FilesService;
import com.ajie.ftpserver.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
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
        R r = filesService.downloadFile(fileUUID);
        FilesPojo filesPojo =(FilesPojo) r.get("file");
        if (null != filesPojo){
            String fileName = filesPojo.getFileOldName();// 文件名
            if (fileName != null) {
                //设置文件路径
                File file = new File(filesPojo.getFilePath());
                //File file = new File(realPath , fileName);
                if (file.exists()) {
                    response.setContentType("application/force-download");// 设置强制下载不打开
                    response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
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
                        return r.put("msg","下载成功");
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
        return r;

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

