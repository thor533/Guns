package com.tuling.pqb.modular.bus.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuling.pqb.modular.bus.mapper.UserCollectionQuestionMapper;
import com.tuling.pqb.modular.bus.entity.UserCollectionQuestion;
import com.tuling.pqb.modular.bus.service.UserCollectionQuestionService;
@Service
public class UserCollectionQuestionServiceImpl extends ServiceImpl<UserCollectionQuestionMapper, UserCollectionQuestion> implements UserCollectionQuestionService{

    @Override
    public int updateBatch(List<UserCollectionQuestion> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<UserCollectionQuestion> list) {
        return baseMapper.batchInsert(list);
    }
}
