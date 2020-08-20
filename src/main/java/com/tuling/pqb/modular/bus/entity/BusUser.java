package com.tuling.pqb.modular.bus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 使用用户的基本信息
 */
@Data
@TableName(value = "bus_user")
public class BusUser implements Serializable {
    /**
     * 人员编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 手机号码
     */
    @TableField(value = "phone_number")
    private String phoneNumber;

    /**
     * 人员姓名
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 当前答题所处的位置
     */
    @TableField(value = "question_id")
    private Integer questionId;

    /**
     * 答题历史最高分
     */
    @TableField(value = "score")
    private Integer score;

    /**
     * 答题历史最高分所用时间
     */
    @TableField(value = "answer_min")
    private Long answerMin;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_PHONE_NUMBER = "phone_number";

    public static final String COL_USER_NAME = "user_name";

    public static final String COL_PASSWORD = "password";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_QUESTION_ID = "question_id";
}
