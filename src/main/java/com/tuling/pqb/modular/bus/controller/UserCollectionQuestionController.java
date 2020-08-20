package com.tuling.pqb.modular.bus.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import com.tuling.pqb.modular.bus.entity.UserCollectionQuestion;
import com.tuling.pqb.modular.bus.service.UserCollectionQuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 人员收藏的相关题目控制器
 *
 * @author gaohan
 * @Date 2020-07-14 14:43:45
 */
@Controller
@RequestMapping("/userCollectionQuestion")
public class UserCollectionQuestionController extends BaseController {

    private String PREFIX = "/modular/bus/userCollectionQuestion/";

    @Autowired
    private UserCollectionQuestionService userCollectionQuestionService;

    /**
     * 跳转到人员收藏的相关题目首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "userCollectionQuestion.html";
    }

    /**
     * 跳转到添加人员收藏的相关题目
     */
    @RequestMapping("/userCollectionQuestion_add")
    public String userCollectionQuestionAdd() {
        return PREFIX + "userCollectionQuestion_add.html";
    }

    /**
     * 跳转到修改人员收藏的相关题目
     */
    @RequestMapping("/userCollectionQuestion_update/{userCollectionQuestionId}")
    public String userCollectionQuestionUpdate(@PathVariable Integer userCollectionQuestionId, Model model) {
        UserCollectionQuestion userCollectionQuestion = userCollectionQuestionService.getById(userCollectionQuestionId);
        model.addAttribute("item",userCollectionQuestion);
        return PREFIX + "userCollectionQuestion_edit.html";
    }

    /**
     * 获取人员收藏的相关题目列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return userCollectionQuestionService.list(null);
    }

    /**
     * 新增人员收藏的相关题目
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(UserCollectionQuestion userCollectionQuestion) {
        userCollectionQuestionService.save(userCollectionQuestion);
        return SUCCESS_TIP;
    }

    /**
     * 删除人员收藏的相关题目
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer userCollectionQuestionId) {
        userCollectionQuestionService.removeById(userCollectionQuestionId);
        return SUCCESS_TIP;
    }

    /**
     * 修改人员收藏的相关题目
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(UserCollectionQuestion userCollectionQuestion) {
        userCollectionQuestionService.updateById(userCollectionQuestion);
        return SUCCESS_TIP;
    }

    /**
     * 人员收藏的相关题目详情
     */
    @RequestMapping(value = "/detail/{userCollectionQuestionId}")
    @ResponseBody
    public Object detail(@PathVariable("userCollectionQuestionId") Integer userCollectionQuestionId) {
        return userCollectionQuestionService.getById(userCollectionQuestionId);
    }
}
