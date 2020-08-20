package com.tuling.pqb.modular.bus.service;

import java.util.List;

import com.tuling.pqb.modular.bus.entity.QuestionBank;
import com.tuling.pqb.modular.bus.entity.UserErrorQuestion;
import com.baomidou.mybatisplus.extension.service.IService;
import org.omg.PortableInterceptor.INACTIVE;

public interface UserErrorQuestionService extends IService<UserErrorQuestion>{


    int updateBatch(List<UserErrorQuestion> list);

    int batchInsert(List<UserErrorQuestion> list);

    void removeBatch( Integer userId,List<UserErrorQuestion> list);

    /**
     * 随机抽取指定数量错题
     * @param userId 用户id
     * @return  List<QuestionBank>
     */
    List<QuestionBank> generateRandomErrorQuestion(Integer userId);

    /**
     * 错题回答正确
     * @param userErrorQuestionIds 错题id
     * @return
     */
    Integer answerRight(List<Integer> userErrorQuestionIds);
}
