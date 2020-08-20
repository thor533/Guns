package com.tuling.pqb.modular.bus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
    * 第三方用户关系表
    */
@Data
@TableName(value = "third_user_relation")
public class ThirdUserRelation implements Serializable {
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 第三方账户ID
     */
    @TableField(value = "open_id")
    private String openId;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 用户类型(1.劳工人员，2.考勤员)
     */
    @TableField(value = "user_type")
    private Integer userType;

    /**
     * 状态 0:禁用 1:启用
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 第三方账户平台标识 0:微信
     */
    @TableField(value = "open_id_platform")
    private String openIdPlatform;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_OPEN_ID = "open_id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_USER_TYPE = "user_type";

    public static final String COL_STATUS = "status";

    public static final String COL_OPEN_ID_PLATFORM = "open_id_platform";
}