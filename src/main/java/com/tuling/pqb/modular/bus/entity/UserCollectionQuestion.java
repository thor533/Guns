package com.tuling.pqb.modular.bus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 * 人员收藏的相关题目
 */
@Data
@TableName(value = "bus_user_collection_question")
public class UserCollectionQuestion implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 人员编号
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 收藏题目编号
     */
    @TableField(value = "collection_question_id")
    private Integer collectionQuestionId;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_COLLECTION_QUESTION_ID = "collection_question_id";
}