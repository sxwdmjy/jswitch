package com.jswitch.service.service;

import com.jswitch.service.domain.Subscriber;

/**
 * 订阅用户表(Subscriber)表服务接口
 *
 * @author danmo
 * @since 2024-06-03 11:27:36
 */
public interface ISubscriberService extends IBaseService<Subscriber> {

    Subscriber getUserByUsername(String username);
}

