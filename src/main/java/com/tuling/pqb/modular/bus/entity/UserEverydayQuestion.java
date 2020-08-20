package com.tuling.pqb.modular.bus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
@TableName(value = "bus_user_everyday_question")
public class UserEverydayQuestion implements Serializable {
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 人员编号
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 每日产生的题目编号,逗号分隔
     */
    @TableField(value = "question_id")
    private String questionId;

    /**
     * 日期
     */
    @TableField(value = "create_date")
    private Date createDate;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_QUESTION_ID = "question_id";

    public static final String COL_CREATE_DATE = "create_date";
}