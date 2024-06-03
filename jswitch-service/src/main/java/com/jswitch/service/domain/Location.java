package com.jswitch.service.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户注册信息表(Location)表实体类
 *
 * @author danmo
 * @since 2024-06-03 11:27:33
 */
@Data
@SuppressWarnings("serial")
@TableName("location")
public class Location extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 729971390045605318L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;


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
     * 内容
     */
    @TableField("contact")
    private String contact;


    /**
     * 接收地址
     */
    @TableField("received")
    private String received;


    /**
     * 有效时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("expires")
    private Date expires;


    /**
     * 会话唯一ID
     */
    @TableField("call_id")
    private String callId;


    /**
     * cseq
     */
    @TableField("cseq")
    private Integer cseq;


    /**
     * 状态 0-在线  1-下线
     */
    @TableField("status")
    private Integer status;


}

