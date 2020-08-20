package com.tuling.pqb.modular.bus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuling.pqb.modular.bus.entity.QuestionBank;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface QuestionBankMapper extends BaseMapper<QuestionBank> {
    int updateBatch(List<QuestionBank> list);

    int batchInsert(@Param("list") List<QuestionBank> list);

    List<QuestionBank> queryQuestionByType(@Param("type") String type);

    List<QuestionBank> queryQuestionScope(@Param("id") Integer id);
}