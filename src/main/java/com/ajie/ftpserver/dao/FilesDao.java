package com.ajie.ftpserver.dao;

import com.ajie.ftpserver.pojo.FilesPojo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName FilesDao
 * @Description
 * @Author septzhang
 * @Date 2022/2/12 23:07
 * @Version 1.0
 **/
@Mapper
public interface FilesDao extends BaseMapper<FilesPojo> {
}
