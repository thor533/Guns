package com.tuling.pqb.modular.bus.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import com.tuling.pqb.modular.bus.entity.UserEverydayQuestion;
import com.tuling.pqb.modular.bus.service.UserEverydayQuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 每日产生的题目编号控制器
 *
 * @author gaohan
 * @Date 2020-07-14 14:45:13
 */
@Controller
@RequestMapping("/userEverydayQuestion")
public class UserEverydayQuestionController extends BaseController {

    private String PREFIX = "/bus/userEverydayQuestion/";

    @Autowired
    private UserEverydayQuestionService userEverydayQuestionService;

    /**
     * 跳转到每日产生的题目编号首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "userEverydayQuestion.html";
    }

    /**
     * 跳转到添加每日产生的题目编号
     */
    @RequestMapping("/userEverydayQuestion_add")
    public String userEverydayQuestionAdd() {
        return PREFIX + "userEverydayQuestion_add.html";
    }

    /**
     * 跳转到修改每日产生的题目编号
     */
    @RequestMapping("/userEverydayQuestion_update/{userEverydayQuestionId}")
    public String userEverydayQuestionUpdate(@PathVariable Integer userEverydayQuestionId, Model model) {
        UserEverydayQuestion userEverydayQuestion = userEverydayQuestionService.getById(userEverydayQuestionId);
        model.addAttribute("item",userEverydayQuestion);
        return PREFIX + "userEverydayQuestion_edit.html";
    }

    /**
     * 获取每日产生的题目编号列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return userEverydayQuestionService.list(null);
    }

    /**
     * 新增每日产生的题目编号
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(UserEverydayQuestion userEverydayQuestion) {
        userEverydayQuestionService.save(userEverydayQuestion);
        return SUCCESS_TIP;
    }

    /**
     * 删除每日产生的题目编号
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer userEverydayQuestionId) {
        userEverydayQuestionService.removeById(userEverydayQuestionId);
        return SUCCESS_TIP;
    }

    /**
     * 修改每日产生的题目编号
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(UserEverydayQuestion userEverydayQuestion) {
        userEverydayQuestionService.updateById(userEverydayQuestion);
        return SUCCESS_TIP;
    }

    /**
     * 每日产生的题目编号详情
     */
    @RequestMapping(value = "/detail/{userEverydayQuestionId}")
    @ResponseBody
    public Object detail(@PathVariable("userEverydayQuestionId") Integer userEverydayQuestionId) {
        return userEverydayQuestionService.getById(userEverydayQuestionId);
    }
}
