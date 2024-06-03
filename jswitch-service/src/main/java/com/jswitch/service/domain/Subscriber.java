package com.jswitch.service.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


/**
 * 订阅用户表(Subscriber)表实体类
 *
 * @author danmo
 * @since 2024-06-03 11:27:36
 */
@Data
@SuppressWarnings("serial")
@TableName("subscriber")
public class Subscriber extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -47591146753786142L;

    /**
     * ID
     */

    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 用户名称
     */
    @TableField("username")
    private String username;


    /**
     * 用户地址
     */
    @TableField("domain")
    private String domain;


    /**
     * 用户密码
     */
    @TableField("password")
    private String password;


    /**
     * 用户状态 0-启用 1-禁用
     */
    @TableField("status")
    private Integer status;


    /**
     *
     */
    @TableField("ha1")
    private String ha1;


    /**
     *
     */
    @TableField("ha1b")
    private String ha1b;


}

