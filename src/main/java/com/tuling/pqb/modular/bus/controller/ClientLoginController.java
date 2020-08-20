package com.tuling.pqb.modular.bus.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tuling.pqb.core.common.exception.BizExceptionEnum;
import com.tuling.pqb.core.util.Md5Util;
import com.tuling.pqb.modular.bus.entity.BusUser;
import com.tuling.pqb.modular.bus.entity.ThirdUserRelation;
import com.tuling.pqb.modular.bus.service.BusUserService;
import com.tuling.pqb.modular.bus.service.ThirdUserRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/clientLogin")
/**
 * @author 张志远
 */
public class ClientLoginController extends BaseController {

    @Autowired
    ThirdUserRelationService thirdUserRelationService;
    @Autowired
    BusUserService busUserService;

    /**
     * 账号和密码进行登录
     * @param userName
     * @param password
     * @param openId
     * @return
     * @throws Exception
     */
    @RequestMapping("/login")
    @ResponseBody
    public BusUser login(String userName, String password, String openId) {
        if (ToolUtil.isEmpty(userName)) {
            ServiceException serviceException = new ServiceException(400, "登录账号不能为空");
            throw serviceException;
        }
        if (ToolUtil.isEmpty(password)) {
            ServiceException serviceException = new ServiceException(400, "密码不能为空");
            throw serviceException;
        }
        if (ToolUtil.isEmpty(openId)) {
            ServiceException serviceException = new ServiceException(400, "微信openId不能为空");
            throw serviceException;
        }
        QueryWrapper wrapperUser = new QueryWrapper<BusUser>();
        wrapperUser.eq("phone_number", userName);
        wrapperUser.eq("password", Md5Util.cryptMD5(password));
        BusUser busUser = busUserService.getOne(wrapperUser);
        if (busUser != null) {
            //先删除,再添加,否则如果同一个账密用不同手机登录，就会出问题
            QueryWrapper wrapperRelation = new QueryWrapper<ThirdUserRelation>();
            wrapperRelation.eq("open_id", openId);
            thirdUserRelationService.remove(wrapperRelation);
            //先往第三方关系表中插入对应的数据,便于之后根据openId进行获取
            ThirdUserRelation tur=new ThirdUserRelation();
            tur.setOpenId(openId);
            tur.setUserId(busUser.getId());
            thirdUserRelationService.save(tur);

        } else {
            ServiceException serviceException = new ServiceException(400,
                    "账号密码错误,未获取到相关人员信息");
            throw serviceException;
        }
        return busUser;
    }


    /**
     * 根据openId进行登录
     *
     * @param openId
     * @return
     * @throws Exception
     */
    @RequestMapping("/loginByOpenId")
    @ResponseBody
    public BusUser loginByOpenId(String openId) {
        if (ToolUtil.isEmpty(openId)) {
            throw new ServiceException(BizExceptionEnum.OPEN_ID_EMPTY);
        }
        //根据openId获取相关人员信息
        QueryWrapper wrapperRelation = new QueryWrapper<ThirdUserRelation>();
        wrapperRelation.eq("open_id", openId);
        ThirdUserRelation thirdUserRelation = thirdUserRelationService.getOne(wrapperRelation);
        if (thirdUserRelation == null) {
            throw new ServiceException(BizExceptionEnum.OPEN_ID_USER_EMPTY);
        }
        Long userId = thirdUserRelation.getUserId();
        QueryWrapper wrapperUser = new QueryWrapper<BusUser>();
        wrapperUser.eq("id", userId);
        BusUser busUser = busUserService.getOne(wrapperUser);
        return busUser;
    }


    /**
     * 微信小程序登出
     *
     * @param userId
     * @return
     */
    @RequestMapping("/wechat/logout")
    @ResponseBody
    public ResponseData weChatLogout(@RequestParam("userId") Long userId) {
        try {
            QueryWrapper wrapperRelation = new QueryWrapper<ThirdUserRelation>();
            wrapperRelation.eq("user_id", userId);
            thirdUserRelationService.remove(wrapperRelation);
            return ResponseData.success(new Object());
        } catch (Exception e) {
            return ResponseData.error("登出失败,错误原因为：" + e.getMessage());
        }
    }
}
