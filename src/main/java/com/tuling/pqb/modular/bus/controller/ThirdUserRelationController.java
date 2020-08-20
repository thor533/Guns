package com.tuling.pqb.modular.bus.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import com.tuling.pqb.modular.bus.entity.ThirdUserRelation;
import com.tuling.pqb.modular.bus.service.ThirdUserRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 第三方用户关系表控制器
 *
 * @author gaohan
 * @Date 2020-07-14 14:44:14
 */
@Controller
@RequestMapping("/thirdUserRelation")
public class ThirdUserRelationController extends BaseController {

    private String PREFIX = "/modular/bus/thirdUserRelation/";

    @Autowired
    private ThirdUserRelationService thirdUserRelationService;

    /**
     * 跳转到第三方用户关系表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "thirdUserRelation.html";
    }

    /**
     * 跳转到添加第三方用户关系表
     */
    @RequestMapping("/thirdUserRelation_add")
    public String thirdUserRelationAdd() {
        return PREFIX + "thirdUserRelation_add.html";
    }

    /**
     * 跳转到修改第三方用户关系表
     */
    @RequestMapping("/thirdUserRelation_update/{thirdUserRelationId}")
    public String thirdUserRelationUpdate(@PathVariable Integer thirdUserRelationId, Model model) {
        ThirdUserRelation thirdUserRelation = thirdUserRelationService.getById(thirdUserRelationId);
        model.addAttribute("item",thirdUserRelation);
        return PREFIX + "thirdUserRelation_edit.html";
    }

    /**
     * 获取第三方用户关系表列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return thirdUserRelationService.list(null);
    }

    /**
     * 新增第三方用户关系表
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ThirdUserRelation thirdUserRelation) {
        thirdUserRelationService.save(thirdUserRelation);
        return SUCCESS_TIP;
    }

    /**
     * 删除第三方用户关系表
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer thirdUserRelationId) {
        thirdUserRelationService.removeById(thirdUserRelationId);
        return SUCCESS_TIP;
    }

    /**
     * 修改第三方用户关系表
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ThirdUserRelation thirdUserRelation) {
        thirdUserRelationService.updateById(thirdUserRelation);
        return SUCCESS_TIP;
    }

    /**
     * 第三方用户关系表详情
     */
    @RequestMapping(value = "/detail/{thirdUserRelationId}")
    @ResponseBody
    public Object detail(@PathVariable("thirdUserRelationId") Integer thirdUserRelationId) {
        return thirdUserRelationService.getById(thirdUserRelationId);
    }
}
