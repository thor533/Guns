package com.tuling.pqb.modular.bus.service;

import java.util.List;
import com.tuling.pqb.modular.bus.entity.UserEverydayQuestion;
import com.baomidou.mybatisplus.extension.service.IService;
public interface UserEverydayQuestionService extends IService<UserEverydayQuestion>{


    int updateBatch(List<UserEverydayQuestion> list);

    int batchInsert(List<UserEverydayQuestion> list);

}
