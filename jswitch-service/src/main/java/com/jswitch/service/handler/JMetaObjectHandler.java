package com.jswitch.service.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author danmo
 * @description 填充器
 * @date 2024/6/4 21:42
 **/
@Slf4j
@Component
public class JMetaObjectHandler implements MetaObjectHandler {
    private static final String CREATE_TIME = "createTime";
    private static final String CREATE_BY = "createBy";
    private static final String UPDATE_TIME = "updateTime";
    private static final String UPDATE_BY = "updateBy";

    @Override
    public void insertFill(MetaObject metaObject) {
        //创建时间
        this.setFieldValByName(CREATE_TIME, new Date(), metaObject);
        this.setFieldValByName(UPDATE_TIME, new Date(), metaObject);
        //创建人
        Long userId = getUserId();

        if (StringUtils.isEmpty((String) this.getFieldValByName(CREATE_BY, metaObject))) {
            this.setFieldValByName(CREATE_BY, userId, metaObject);
        }

        if (StringUtils.isEmpty((String) this.getFieldValByName(UPDATE_BY, metaObject))) {
            this.setFieldValByName(UPDATE_BY, userId, metaObject);
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //更新时间
        this.setFieldValByName(UPDATE_TIME, new Date(), metaObject);
        //更新人
        this.setFieldValByName(UPDATE_BY, getUserId(), metaObject);
    }


    private Long getUserId() {
        return 0L;
    }
}
