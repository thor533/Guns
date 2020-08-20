package com.tuling.pqb.modular.bus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tuling.pqb.modular.bus.entity.QuestionBank;
import com.tuling.pqb.modular.bus.mapper.QuestionBankMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuling.pqb.modular.bus.mapper.UserErrorQuestionMapper;
import com.tuling.pqb.modular.bus.entity.UserErrorQuestion;
import com.tuling.pqb.modular.bus.service.UserErrorQuestionService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserErrorQuestionServiceImpl extends ServiceImpl<UserErrorQuestionMapper, UserErrorQuestion> implements UserErrorQuestionService{

    @Resource
    UserErrorQuestionMapper userErrorQuestionMapper;
    @Resource
    private QuestionBankMapper questionBankMapper;
    @Override
    public int updateBatch(List<UserErrorQuestion> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<UserErrorQuestion> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public void removeBatch( Integer userId,List<UserErrorQuestion> list) {
        userErrorQuestionMapper.removeBatch(userId,list);
    }
    @Override
    public List<QuestionBank> generateRandomErrorQuestion(Integer userId) {
        QueryWrapper<UserErrorQuestion> errorQuestionQueryWrapper = new QueryWrapper<>();
        errorQuestionQueryWrapper.eq(UserErrorQuestion.COL_USER_ID, userId);
        errorQuestionQueryWrapper.orderByAsc(UserErrorQuestion.COL_ERROR_QUESTION_ID);
        List<UserErrorQuestion> errorQuestions = this.baseMapper.selectList(errorQuestionQueryWrapper);
        if (errorQuestions.isEmpty()){
            return new ArrayList<>();
        }
        List<Integer> idList = errorQuestions.stream().map(UserErrorQuestion::getErrorQuestionId).collect(Collectors.toList());
        Collections.shuffle(idList);
        QueryWrapper<QuestionBank> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(QuestionBank.COL_ID,idList);
        return this.questionBankMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer answerRight(List<Integer> userErrorQuestionIds) {
        return questionBankMapper.deleteBatchIds(userErrorQuestionIds);

    }
}
