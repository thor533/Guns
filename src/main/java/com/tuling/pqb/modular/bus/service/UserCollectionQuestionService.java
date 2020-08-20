package com.tuling.pqb.modular.bus.service;

import java.util.List;
import com.tuling.pqb.modular.bus.entity.UserCollectionQuestion;
import com.baomidou.mybatisplus.extension.service.IService;
public interface UserCollectionQuestionService extends IService<UserCollectionQuestion>{


    int updateBatch(List<UserCollectionQuestion> list);

    int batchInsert(List<UserCollectionQuestion> list);

}
