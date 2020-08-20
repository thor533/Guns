package com.tuling.pqb.modular.bus.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuling.pqb.modular.bus.mapper.UserEverydayQuestionMapper;
import com.tuling.pqb.modular.bus.entity.UserEverydayQuestion;
import com.tuling.pqb.modular.bus.service.UserEverydayQuestionService;
@Service
public class UserEverydayQuestionServiceImpl extends ServiceImpl<UserEverydayQuestionMapper, UserEverydayQuestion> implements UserEverydayQuestionService{

    @Override
    public int updateBatch(List<UserEverydayQuestion> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<UserEverydayQuestion> list) {
        return baseMapper.batchInsert(list);
    }
}
