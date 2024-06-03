package com.jswitch.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.jswitch.service.domain.Location;

/**
 * 用户注册信息表(Location)表数据库访问层
 *
 * @author danmo
 * @since 2024-06-03 11:27:23
 */
@Repository()
@Mapper
public interface LocationMapper extends BaseMapper<Location> {

}

