package com.ajie.ftpserver.service.impl;

import com.ajie.ftpserver.dao.FilesDao;
import com.ajie.ftpserver.pojo.FilesPojo;
import com.ajie.ftpserver.service.FilesService;
import com.ajie.ftpserver.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @ClassName FilesServiceImpl
 * @Description
 * @Author septzhang
 * @Date 2022/2/12 23:04
 * @Version 1.0
 **/
@Service("FilesService")
public class FilesServiceImpl extends ServiceImpl<FilesDao,FilesPojo> implements FilesService {

    private final static Logger logger =LoggerFactory.getLogger(FilesServiceImpl.class);
    public R saveFile(MultipartFile file){
        /**
         * 判断上传文件是否为空
         */
        if (file.isEmpty()){
            return R.error(403,"未选择文件");
        }
        /**
         * 获取上传文件生成的UUID名 fileUuidName
         */
        String fileUuidName = UUID.randomUUID().toString().replaceAll("-", "");
        /**
         * 获取日期文件的格式
         */
        SimpleDateFormat formatter= new SimpleDateFormat("yyyyMMdd");
        Date date = new Date(System.currentTimeMillis());
        /**
         * 设置存储在服务器端的路径
         */
        String filePath = "/resources/temp/"+formatter.format(date)+"/";
        File temp = new File(filePath);
        /**
         * 文件路径不存在，则创建
         */
        if (!temp.exists() || !temp.getParentFile().exists()){
            logger.info("创建路径：" + temp.getAbsolutePath());
            temp.mkdirs();
        }
        /**
         * 存储文件
         */
        File localFile = new File(temp.getAbsolutePath()+"/"+fileUuidName+"."+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1));
        try {
            /**
             * 把上传的文件保存至本地
             */
            file.transferTo(localFile);
            /**
             * 把文件信息写入到数据库
             */
            FilesPojo filesPojo = new FilesPojo();
            filesPojo.setFileSize(file.getSize());
            filesPojo.setFilePath(localFile.getAbsolutePath());
            filesPojo.setFileType(file.getContentType());
            filesPojo.setFileOldName(file.getOriginalFilename());
            filesPojo.setFileUuid(fileUuidName);
            save(filesPojo);
            logger.info("数据写入：" + filesPojo.toString());
        }catch (IOException e){
            e.printStackTrace();
            logger.error("文件上传失败：" + file.getOriginalFilename());
            return R.error(500,"文件上传失败");
        }
        logger.info("文件上传成功：" + temp.getAbsolutePath()+temp.getName());
        return R.ok().put("msg",file.getOriginalFilename()+"文件上传成功");
    }


    /**
     * 下载文件
     * @param fileUUID
     * @return
     */
    @Override
    public FilesPojo downloadFile(String fileUUID) {

        QueryWrapper<FilesPojo> queryWrapper = new QueryWrapper<>();
        /**
         * 查询列名为file_uuid的记录
         */
        queryWrapper.eq("file_uuid",fileUUID);
        FilesPojo filesPojo = getOne(queryWrapper);
        logger.info("数据查询：" + fileUUID);

        return filesPojo;
    }

    /**
     * 查询文件信息
     * @param fileUUID
     * @return
     */
    @Override
    public R getInfoFile(String fileUUID) {
        QueryWrapper<FilesPojo> queryWrapper = new QueryWrapper<>();
        /**
         * 查询列名为file_uuid的记录
         */
        queryWrapper.eq("file_uuid",fileUUID);
        FilesPojo filesPojo = getOne(queryWrapper);
        logger.info("数据查询：" + fileUUID);
        /**
         * 查询成功，则返回对象
         */
        if (null != filesPojo){
            return R.ok().put("file",filesPojo);
        }
        return R.error(410,"异常响应");
    }
}
