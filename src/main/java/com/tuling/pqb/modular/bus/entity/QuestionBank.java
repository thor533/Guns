package com.tuling.pqb.modular.bus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 题库
 */
@Data
@TableName(value = "bus_question_bank")
public class QuestionBank implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 题目内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 题目类型 1判断 2单选  3多选
     */
    @TableField(value = "type")
    private String type;

    /**
     * 选项a
     */
    @TableField(value = "option_a")
    private String optionA;

    /**
     * 选项b
     */
    @TableField(value = "option_b")
    private String optionB;

    /**
     * 选项c
     */
    @TableField(value = "option_c")
    private String optionC;

    /**
     * 选项d
     */
    @TableField(value = "option_d")
    private String optionD;

    /**
     * 答案,用逗号进行  例如A,B
     */
    @TableField(value = "answer")
    private String answer;

    /**
     * 解析
     */
    @TableField(value = "analysis")
    private String analysis;

    /**
     * 1 党员干部  2 非中共党员公职人员
     */
    @TableField(value = "user_question_type")
    private String userQuestionType;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_CONTENT = "content";

    public static final String COL_TYPE = "type";

    public static final String COL_OPTION_A = "option_a";

    public static final String COL_OPTION_B = "option_b";

    public static final String COL_OPTION_C = "option_c";

    public static final String COL_OPTION_D = "option_d";

    public static final String COL_ANSWER = "answer";

    public static final String COL_ANALYSIS = "analysis";

    public static final String COL_USER_QUESTION_TYPE = "user_question_type";

    public static final String COL_CREATE_TIME = "create_time";
}