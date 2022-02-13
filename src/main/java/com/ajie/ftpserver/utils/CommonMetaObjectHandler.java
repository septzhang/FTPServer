package com.ajie.ftpserver.utils;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ClassName MyMetaObjectHandler
 * @Description
 * @Author septzhang
 * @Date 2022/2/12 23:46
 * @Version 1.0
 **/
@Slf4j
@Component
public class CommonMetaObjectHandler implements MetaObjectHandler {
    /**
     * 创建时间
     */
    private static final String FIELD_SYS_CREATE_TIME = "fileCreatetime";
    /**
     * 修改时间
     */
    private static final String FIELD_SYS_MODIFIED_TIME = "fileModifyTime";

    /**
     * 插入元对象字段填充（用于插入时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Date currentDate = new Date();
        // 插入创建时间
        if (metaObject.hasSetter(FIELD_SYS_CREATE_TIME)) {
            this.strictInsertFill(metaObject, FIELD_SYS_CREATE_TIME, Date.class, currentDate);
        }
        // 同时设置修改时间为当前插入时间
        if (metaObject.hasSetter(FIELD_SYS_MODIFIED_TIME)) {
            this.strictUpdateFill(metaObject, FIELD_SYS_MODIFIED_TIME, Date.class, currentDate);
        }
    }

    /**
     * 更新元对象字段填充（用于更新时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName(FIELD_SYS_MODIFIED_TIME, new Date(), metaObject);
    }
}
