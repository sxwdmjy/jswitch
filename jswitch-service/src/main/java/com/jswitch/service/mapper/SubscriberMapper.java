package com.jswitch.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.jswitch.service.domain.Subscriber;

/**
 * 订阅用户表(Subscriber)表数据库访问层
 *
 * @author danmo
 * @since 2024-06-03 11:27:36
 */
@Repository()
@Mapper
public interface SubscriberMapper extends BaseMapper<Subscriber> {

}

