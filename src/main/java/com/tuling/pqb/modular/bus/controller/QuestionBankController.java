package com.tuling.pqb.modular.bus.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tuling.pqb.core.util.Md5Util;
import com.tuling.pqb.modular.bus.entity.BusUser;
import com.tuling.pqb.modular.bus.entity.QuestionBank;
import com.tuling.pqb.modular.bus.service.QuestionBankService;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

/**
 * 题库控制器
 *
 * @author gaohan
 * @Date 2020-07-14 14:07:56
 */
@Controller
@RequestMapping("/questionBank")
public class QuestionBankController extends BaseController {

    private String PREFIX = "/modular/bus/questionBank/";

    @Autowired
    private QuestionBankService questionBankService;

    /**
     * 跳转到题库首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "questionBank.html";
    }

    /**
     * 跳转到添加题库
     */
    @RequestMapping("/questionBank_add")
    public String questionBankAdd() {
        return PREFIX + "questionBank_add.html";
    }

    /**
     * 跳转到修改题库
     */
    @RequestMapping("/questionBank_update/{questionBankId}")
    public String questionBankUpdate(@PathVariable Integer questionBankId, Model model) {
        QuestionBank questionBank = questionBankService.getById(questionBankId);
        model.addAttribute("item", questionBank);
        return PREFIX + "questionBank_edit.html";
    }

    /**
     * 获取题库列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return questionBankService.list();
    }

    /**
     * 批量获取题库
     */
    @RequestMapping(value = "/listBatch")
    @ResponseBody
    public Object listBatch(String ids) {
        System.out.println(ids);
        String[] idArr = ids.split(",");
        List<String> list1 = Arrays.asList(idArr);
        Collection<QuestionBank> questionBanks = questionBankService.listByIds(list1);
        return questionBanks;
    }

    /**
     * 新增题库
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(QuestionBank questionBank) {
        questionBankService.save(questionBank);
        return SUCCESS_TIP;
    }

    /**
     * 获取相关模拟题库的id集合
     */
    @RequestMapping(value = "/getImitateQuestions")
    @ResponseBody
    public Object getImitateQuestionIds() {
        return getQuestions();
    }

    /**
     * 获取相关挑战题库的id集合
     */
    @RequestMapping(value = "/getChallengeQuestions")
    @ResponseBody
    public Object getChallengeQuestionIds() {
        return getQuestions();
    }

    /**
     * 获取相关学习题目的集合
     */
    @RequestMapping(value = "/queryQuestionScope")
    @ResponseBody
    public Object queryQuestionScope(Integer id){
        List<QuestionBank> questionBanks= questionBankService.queryQuestionScope(id);
        return questionBanks;
    }
    /**
     * 获取相关的集合的题
     *
     * @return
     */
    private List<QuestionBank> getQuestions() {
        List<QuestionBank> resQuestions = new ArrayList<>();
        //获取判断题
        List<QuestionBank> judgeQuestionIds = getIds("1", 10);
        //获取单选题
        List<QuestionBank> singleChoicesIds = getIds("2", 20);
        //获取多选题
        List<QuestionBank> mulChoicesIds = getIds("3", 20);
        resQuestions.addAll(judgeQuestionIds);
        resQuestions.addAll(singleChoicesIds);
        resQuestions.addAll(mulChoicesIds);
        return resQuestions;
    }

    /**
     * 获取对应的题目的id 以及数量
     *
     * @param type
     * @param count
     * @return
     */
    private List<QuestionBank> getIds(String type, int count) {
        List<QuestionBank> ids = questionBankService.queryQuestionByType(type);
        Collections.shuffle(ids);
        ids = ids.subList(0, count);
        return ids;
    }

    /**
     * 删除题库
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer questionBankId) {
        questionBankService.removeById(questionBankId);
        return SUCCESS_TIP;
    }

    /**
     * 修改题库
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(QuestionBank questionBank) {
        questionBankService.updateById(questionBank);
        return SUCCESS_TIP;
    }

    /**
     * 题库详情
     */
    @RequestMapping(value = "/detail/{questionBankId}")
    @ResponseBody
    public Object detail(@PathVariable("questionBankId") Integer questionBankId) {
        return questionBankService.getById(questionBankId);
    }
}
