package com.jswitch.service.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jswitch.service.domain.BaseEntity;
import com.jswitch.service.domain.Location;
import com.jswitch.service.mapper.LocationMapper;
import com.jswitch.service.service.ILocationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 用户注册信息表(Location)表服务实现类
 *
 * @author danmo
 * @since 2024-06-03 11:27:28
 */
@Service
public class LocationServiceImpl extends BaseServiceImpl<LocationMapper, Location> implements ILocationService {

    @Override
    public Boolean add(Location location) {
        Location oldLocation = getOne(new LambdaQueryWrapper<Location>()
                .eq(Location::getUsername, location.getUsername())
                .eq(Location::getCallId,location.getCallId())
                .eq(BaseEntity::getDelFlag, 0)
                .last("limit 1"));
        if (Objects.isNull(oldLocation)) {
            return save(location);
        } else {
            location.setId(oldLocation.getId());
            return updateById(location);
        }
    }

    @Override
    public Boolean delete(Location location) {
        location.setDelFlag(1);
        location.setStatus(1);
        return update(location,new LambdaQueryWrapper<Location>().eq(Location::getUsername,location.getUsername()).eq(Location::getCallId,location.getCallId()));
    }

    @Override
    public Boolean checkUserName(String username)
    {
        return count(new LambdaQueryWrapper<Location>()
                .eq(Location::getUsername, username)
                .le(Location::getExpires, DateUtil.now())
                .eq(BaseEntity::getDelFlag, 0)) > 0L;
    }

    @Override
    public Location getByUserName(String username) {
       return getOne(new LambdaQueryWrapper<Location>()
                .eq(Location::getUsername, username)
                .ge(Location::getExpires, DateUtil.now())
                .eq(BaseEntity::getDelFlag, 0)
                .last(" limit 1"));
    }
}

