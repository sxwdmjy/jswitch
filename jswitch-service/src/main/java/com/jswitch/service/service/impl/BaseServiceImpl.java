package com.jswitch.service.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.jswitch.common.utils.SQLFilter;
import com.jswitch.service.service.IBaseService;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;

/**
 * @author danmo
 * @date 2023年07月06日 23:01
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements IBaseService<T> {

    /**
     * 设置分页
     *
     * @param pageIndex 页码
     * @param pageSize  条数
     */
    protected void startPage(Integer pageIndex, Integer pageSize) {
        startPage(pageIndex, pageSize, null, null);
    }

    /**
     * 设置分页
     *
     * @param pageIndex 页码
     * @param pageSize  条数
     * @param sortField 排序字段
     * @param sort      排序方式
     */
    public void startPage(Integer pageIndex, Integer pageSize, String sortField, String sort) {
        LinkedHashMap<String, String> sortMap = new LinkedHashMap<String, String>();
        if (StringUtils.isNotEmpty(sortField)) {
            if (StringUtils.isEmpty(sort)) {
                sort = "asc";
            }
            sortMap.put(sortField, sort);
        }
        startPage(pageIndex, pageSize, sortMap);
    }

    /**
     * 设置分页
     *
     * @param pageIndex 页码
     * @param pageSize  条数
     * @param sortMap   排序字段及 排序方式
     */
    public void startPage(Integer pageIndex, Integer pageSize, LinkedHashMap<String, String> sortMap) {
        if (pageIndex == null || pageIndex < 1) {
            pageIndex = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }

        PageHelper.startPage(pageIndex, pageSize);

        StringBuilder orderByStr = new StringBuilder();
        if (sortMap != null && sortMap.size() > 0) {
            for (String key : sortMap.keySet()) {
                String filed = SQLFilter.sqlInject(key);
                String ascOrDesc = SQLFilter.sqlInject(sortMap.get(key));
                if (StringUtils.isEmpty(ascOrDesc)) {
                    ascOrDesc = " asc";
                }
                orderByStr.append(" ").append(filed).append(" ").append(ascOrDesc).append(",");
            }

            if (StringUtils.isNotEmpty(orderByStr.toString())) {
                orderByStr = new StringBuilder(orderByStr.substring(0, orderByStr.length() - 1));
                PageHelper.orderBy(orderByStr.toString());
            }
        }
    }
}
