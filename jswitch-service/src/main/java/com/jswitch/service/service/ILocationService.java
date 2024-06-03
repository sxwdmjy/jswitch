package com.jswitch.service.service;

import com.jswitch.service.domain.Location;

/**
 * 用户注册信息表(Location)表服务接口
 *
 * @author danmo
 * @since 2024-06-03 11:27:26
 */
public interface ILocationService extends IBaseService<Location> {

    Boolean add(Location location);

    Boolean delete(Location location);

    Location getByUserName(String username);
}

