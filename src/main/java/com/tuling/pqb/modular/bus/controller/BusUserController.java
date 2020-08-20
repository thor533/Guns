package com.tuling.pqb.modular.bus.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuling.pqb.core.util.BusUtil;
import com.tuling.pqb.core.util.Md5Util;
import com.tuling.pqb.modular.bus.dto.BusUserAndRelationDto;
import com.tuling.pqb.modular.bus.entity.BusUser;
import com.tuling.pqb.modular.bus.entity.ThirdUserRelation;
import com.tuling.pqb.modular.bus.service.BusUserService;
import com.tuling.pqb.modular.bus.service.SmsService;
import com.tuling.pqb.modular.bus.service.ThirdUserRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 使用用户的基本信息控制器
 *
 * @author gaohan
 * @Date 2020-07-14 14:46:41
 */
@Controller
@RequestMapping("/busUser")
public class BusUserController extends BaseController {

    private String PREFIX = "/modular/bus/busUser/";

    @Autowired
    private BusUserService busUserService;
    @Autowired
    private ThirdUserRelationService thirdUserRelationService;
    @Autowired
    private SmsService smsService;

    /**
     * 跳转到使用用户的基本信息首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "busUser.html";
    }

    /**
     * 跳转到添加使用用户的基本信息
     */
    @RequestMapping("/busUser_add")
    public String busUserAdd() {
        return PREFIX + "busUser_add.html";
    }

    /**
     * 跳转到修改使用用户的基本信息
     */
    @RequestMapping("/busUser_update/{busUserId}")
    public String busUserUpdate(@PathVariable Integer busUserId, Model model) {
        BusUser busUser = busUserService.getById(busUserId);
        model.addAttribute("item", busUser);
        return PREFIX + "busUser_edit.html";
    }

    /**
     * 获取使用用户的基本信息列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return busUserService.list(null);
    }

    /**
     * 新增使用用户的基本信息
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(@RequestBody BusUserAndRelationDto busUserAndRelationDto) {
        //验证码校验
        Boolean checkValidateCode = smsService.checkValidateCode(busUserAndRelationDto.getPhoneNumber(), busUserAndRelationDto.getSmsCode());
        if (!checkValidateCode){
            return ResponseData.error("验证码不正确");
        }
        QueryWrapper wrapperUser = new QueryWrapper<BusUser>();
        wrapperUser.eq(BusUser.COL_PHONE_NUMBER, busUserAndRelationDto.getPhoneNumber());
        BusUser busUserExist = busUserService.getOne(wrapperUser);
        if (busUserExist != null) {
            return ResponseData.error("该手机号码已经进行了注册");
        }
        QueryWrapper relationQueryWrapper = new QueryWrapper<ThirdUserRelation>();
        relationQueryWrapper.eq(ThirdUserRelation.COL_OPEN_ID,busUserAndRelationDto.getOpenId());
        ThirdUserRelation thirdUserRelation = thirdUserRelationService.getOne(relationQueryWrapper);
        if (ToolUtil.isNotEmpty(thirdUserRelation)){
            throw new ServiceException(400, "当前用户微信已绑定其他手机号");
        }
        String password=busUserAndRelationDto.getPassword();
        busUserAndRelationDto.setPassword(Md5Util.cryptMD5(password));;
        BusUser busUser = busUserService.saveUserAndRelation(busUserAndRelationDto);
        return ResponseData.success(busUser);
    }

    /**
     * 修改面貌
     */
    @RequestMapping(value = "/modifyPassword")
    @ResponseBody
    public Object modifyPassword(@RequestBody BusUserAndRelationDto busUserAndRelationDto) {
        //验证码校验
        Boolean checkValidateCode = smsService.checkValidateCode(busUserAndRelationDto.getPhoneNumber(), busUserAndRelationDto.getSmsCode());
        if (!checkValidateCode){
            return ResponseData.error("验证码不正确");
        }
        QueryWrapper wrapperUser = new QueryWrapper<BusUser>();
        wrapperUser.eq(BusUser.COL_PHONE_NUMBER, busUserAndRelationDto.getPhoneNumber());
        BusUser busUserExist = busUserService.getOne(wrapperUser);
        if (busUserExist == null) {
            return ResponseData.error("当前手机号尚未注册");
        }
        busUserExist.setPassword(Md5Util.cryptMD5(busUserAndRelationDto.getPassword()));
        busUserService.updateById(busUserExist);
        return SUCCESS_TIP;
    }

    /**
     * 删除使用用户的基本信息
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer busUserId) {
        busUserService.removeById(busUserId);
        return SUCCESS_TIP;
    }

    /**
     * 修改使用用户的基本信息
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(BusUser busUser) {
        if (ToolUtil.isNotEmpty(busUser)){
            busUserService.updateById(busUser);

        }
        return SUCCESS_TIP;
    }

    /**
     * 使用用户的基本信息详情
     */
    @RequestMapping(value = "/detail/{busUserId}")
    @ResponseBody
    public Object detail(@PathVariable("busUserId") Integer busUserId) {
        return busUserService.getById(busUserId);
    }

    /**
     * 查询排行榜
     * @return
     */
    @RequestMapping(value = "/getCharts")
    @ResponseBody
    public Object getUserCharts(){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("score");
        queryWrapper.orderByAsc("answer_min");

        Page<BusUser> pageWrapper = new Page();
        pageWrapper.setCurrent(1L);
        pageWrapper.setTotal(0L);
        pageWrapper.setSize(20L);

        IPage<BusUser> pageResult = busUserService.page(pageWrapper, queryWrapper);
        List<Map<String, Object>> data = pageResult.getRecords().stream()
                .map(b -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("name", b.getUserName());
                    m.put("score", b.getScore());
                    m.put("answerMin", BusUtil.StringsecondToTime(b.getAnswerMin()));
                    return m;
                }).collect(Collectors.toList());


        return data;
    }

    /**
     * 查询个人排名和收藏题目数量
     * @return
     */
    @RequestMapping(value = "/getUserRanking")
    @ResponseBody
    public Object getUserRanking(String userId){
        QueryWrapper<BusUser> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("score");
        queryWrapper.orderByAsc("answer_min");

        List<Long> userIds = busUserService.list(queryWrapper).stream().map(u -> u.getId()).collect(Collectors.toList());

        Long uid = Long.valueOf(userId);
        int ranking = userIds.indexOf(uid) + 1;
        Map<String, Object> map = new HashMap<>();
        map.put("ranking", ranking);

        return map;
    }


}
