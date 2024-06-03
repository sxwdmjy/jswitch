package com.jswitch.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jswitch.service.domain.BaseEntity;
import com.jswitch.service.domain.Subscriber;
import com.jswitch.service.mapper.SubscriberMapper;
import com.jswitch.service.service.ISubscriberService;
import org.springframework.stereotype.Service;

/**
 * 订阅用户表(Subscriber)表服务实现类
 *
 * @author danmo
 * @since 2024-06-03 11:27:36
 */
@Service
public class SubscriberServiceImpl extends BaseServiceImpl<SubscriberMapper, Subscriber> implements ISubscriberService {

    @Override
    public Subscriber getUserByUsername(String username)
    {
        return getOne(new LambdaQueryWrapper<Subscriber>()
                .eq(Subscriber::getUsername, username)
                .eq(Subscriber::getStatus, 0)
                .eq(BaseEntity::getDelFlag, 0)
                .last(" limit 1"));
    }
}

