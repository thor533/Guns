package com.tuling.pqb.modular.bus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 * 人员错误的相关题目
 *
 * @author gaohan
 */
@Data
@TableName(value = "bus_user_error_question")
public class UserErrorQuestion implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 人员编号
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 错误题目编号
     */
    @TableField(value = "error_question_id")
    private Integer errorQuestionId;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_ERROR_QUESTION_ID = "error_question_id";
}