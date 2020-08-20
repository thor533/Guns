package com.tuling.pqb.modular.bus.service;

import java.util.List;
import com.tuling.pqb.modular.bus.entity.QuestionBank;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

public interface QuestionBankService extends IService<QuestionBank>{


    int updateBatch(List<QuestionBank> list);

    int batchInsert(List<QuestionBank> list);

    List<QuestionBank> queryQuestionByType(String type);

    List<QuestionBank> queryQuestionScope(Integer id);

}
