package com.tuling.pqb.modular.bus.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.alibaba.fastjson.JSONObject;
import com.tuling.pqb.modular.bus.entity.QuestionBank;
import com.tuling.pqb.modular.bus.entity.UserErrorQuestion;
import com.tuling.pqb.modular.bus.service.UserErrorQuestionService;
import io.swagger.models.auth.In;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 人员错误的相关题目控制器
 *
 * @author gaohan
 * @Date 2020-07-14 14:44:36
 */
@Controller
@RequestMapping("/userErrorQuestion")
public class UserErrorQuestionController extends BaseController {

    private String PREFIX = "/modular/bus/userErrorQuestion/";

    @Autowired
    private UserErrorQuestionService userErrorQuestionService;

    /**
     * 跳转到人员错误的相关题目首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "userErrorQuestion.html";
    }

    /**
     * 跳转到添加人员错误的相关题目
     */
    @RequestMapping("/userErrorQuestion_add")
    public String userErrorQuestionAdd() {
        return PREFIX + "userErrorQuestion_add.html";
    }

    /**
     * 跳转到修改人员错误的相关题目
     */
    @RequestMapping("/userErrorQuestion_update/{userErrorQuestionId}")
    public String userErrorQuestionUpdate(@PathVariable Integer userErrorQuestionId, Model model) {
        UserErrorQuestion userErrorQuestion = userErrorQuestionService.getById(userErrorQuestionId);
        model.addAttribute("item",userErrorQuestion);
        return PREFIX + "userErrorQuestion_edit.html";
    }

    /**
     * 获取人员错误的相关题目列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return userErrorQuestionService.list(null);
    }

    /**
     * 新增人员错误的相关题目
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(UserErrorQuestion userErrorQuestion) {
        userErrorQuestionService.save(userErrorQuestion);
        return SUCCESS_TIP;
    }

    /**
     * 批量新增人员错误的相关题目
     */
    @RequestMapping(value = "/addBatch")
    @ResponseBody
    public Object addBatch(Integer userId, String errorIds) {
        String[] ids=errorIds.split(",");
        List<UserErrorQuestion> questions=new ArrayList<>();
        UserErrorQuestion question;
        for(int i=0;i<ids.length;i++){
            question=new UserErrorQuestion();
            question.setErrorQuestionId(Integer.valueOf(ids[i]));
            question.setUserId(userId);
            questions.add(question);
        }
        userErrorQuestionService.batchInsert(questions);
        return SUCCESS_TIP;
    }

    /**
     * 批量删除人员错误的相关题目
     */
    @RequestMapping(value = "/deleteBatch")
    @ResponseBody
    public Object deleteBatch(Integer userId, String errorIds) {
        String[] ids=errorIds.split(",");
        List<UserErrorQuestion> questions=new ArrayList<>();
        UserErrorQuestion question;
        for(int i=0;i<ids.length;i++){
            question=new UserErrorQuestion();
            question.setErrorQuestionId(Integer.valueOf(ids[i]));
            questions.add(question);
        }
        userErrorQuestionService.removeBatch(userId,questions);
        return SUCCESS_TIP;
    }

    /**
     * 删除人员错误的相关题目
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer userErrorQuestionId) {
        userErrorQuestionService.removeById(userErrorQuestionId);
        return SUCCESS_TIP;
    }



    /**
     * 修改人员错误的相关题目
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(UserErrorQuestion userErrorQuestion) {
        userErrorQuestionService.updateById(userErrorQuestion);
        return SUCCESS_TIP;
    }

    /**
     * 人员错误的相关题目详情
     */
    @RequestMapping(value = "/detail/{userErrorQuestionId}")
    @ResponseBody
    public Object detail(@PathVariable("userErrorQuestionId") Integer userErrorQuestionId) {
        return userErrorQuestionService.getById(userErrorQuestionId);
    }

    /**
     * 获取错题
     * @param userId
     * @return
     */
    @RequestMapping("/random")
    @ResponseBody
    public ResponseData generateRandomUserError(Integer userId){
        List<QuestionBank> questionBankList = userErrorQuestionService.generateRandomErrorQuestion(userId);
        return ResponseData.success(questionBankList);
    }

    /**
     * 错题答对
     * @param ids
     * @return
     */
    @RequestMapping("/answerRight")
    @ResponseBody
    public ResponseData answerRight(@RequestBody String ids){
        List<Integer> list = JSONObject.parseArray(ids, Integer.TYPE);
        int count = userErrorQuestionService.answerRight(list);
        if (count==list.size()){
            return SUCCESS_TIP;
        }else {
            return ResponseData.error("错题更新失败");
        }
    }
}
