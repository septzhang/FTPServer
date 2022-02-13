package com.ajie.ftpserver.utils;

import com.ajie.ftpserver.service.impl.FilesServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @ClassName ResponseFileStreamUtils
 * @Description
 * @Author septzhang
 * @Date 2022/2/13 17:42
 * @Version 1.0
 **/
public class ResponseFileStreamUtils {
    private final static Logger logger = LoggerFactory.getLogger(FilesServiceImpl.class);

    /**
     * 响应体加入文件流
     * @param response
     * @param filePath	文件从盘符开始的完整路径
     */
    public static void responseFileStream(HttpServletResponse response, String filePath){
        logger.debug("responseFileStream imgPath:"+filePath);
        if(filePath.contains("%")){
            try {
                filePath = URLDecoder.decode(filePath,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.debug("responseFileStream decode error:"+e.toString());
            }
        }
        if(filePath.contains("%")){
            try {
                filePath = URLDecoder.decode(filePath,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.debug("responseFileStream decode error:"+e.toString());
            }
        }

        ServletOutputStream out = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(new File(filePath));
            String[] dir = filePath.split("/");
            String fileName = dir[dir.length-1];
            String[] array = fileName.split("[.]");
            String fileType = array[array.length-1].toLowerCase();
            /**
             *设置文件ContentType类型
             */
            if("jpg,jepg,gif,png".contains(fileType)){
                response.setContentType("image/"+fileType);
            }else if("pdf".contains(fileType)){
                response.setContentType("application/pdf");
            }else{//自动判断下载文件类型
                response.setContentType("multipart/form-data");
            }
            /**
             *设置文件头：最后一个参数是设置下载文件名
             */
            //response.setHeader("Content-Disposition", "attachment;fileName="+fileName);
            out = response.getOutputStream();
            /**
             * 读取文件流
              */
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (FileNotFoundException e) {
            logger.error("responseFileStream error:FileNotFoundException" + e.toString());
        } catch (Exception e) {
            logger.error("responseFileStream error:" + e.toString());
        } finally {
            try {
                out.close();
                in.close();
            } catch (NullPointerException e) {
                logger.error("responseFileStream stream close() error:NullPointerException" + e.toString());
            } catch (Exception e) {
                logger.error("responseFileStream stream close() error:" + e.toString());
            }
        }
    }
}
