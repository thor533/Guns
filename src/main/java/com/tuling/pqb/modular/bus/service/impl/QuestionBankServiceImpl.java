package com.tuling.pqb.modular.bus.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuling.pqb.modular.bus.mapper.QuestionBankMapper;
import com.tuling.pqb.modular.bus.entity.QuestionBank;
import com.tuling.pqb.modular.bus.service.QuestionBankService;

import javax.annotation.Resource;

@Service
public class QuestionBankServiceImpl extends ServiceImpl<QuestionBankMapper, QuestionBank> implements QuestionBankService{
    @Resource
    QuestionBankMapper questionBankMapper;
    @Override
    public int updateBatch(List<QuestionBank> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<QuestionBank> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public List<QuestionBank> queryQuestionByType(String type) {
        return questionBankMapper.queryQuestionByType(type);
    }

    @Override
    public List<QuestionBank> queryQuestionScope(Integer id) {
        return questionBankMapper.queryQuestionScope(id);
    }
}
