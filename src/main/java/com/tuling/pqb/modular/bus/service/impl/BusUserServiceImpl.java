package com.tuling.pqb.modular.bus.service.impl;

import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tuling.pqb.core.util.Md5Util;
import com.tuling.pqb.modular.bus.dto.BusUserAndRelationDto;
import com.tuling.pqb.modular.bus.entity.ThirdUserRelation;
import com.tuling.pqb.modular.bus.mapper.ThirdUserRelationMapper;
import com.tuling.pqb.modular.bus.service.BusUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuling.pqb.modular.bus.entity.BusUser;
import com.tuling.pqb.modular.bus.mapper.BusUserMapper;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Slf4j
public class BusUserServiceImpl extends ServiceImpl<BusUserMapper, BusUser> implements BusUserService {

    @Override
    public int updateBatch(List<BusUser> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<BusUser> list) {
        return baseMapper.batchInsert(list);
    }

    private ThirdUserRelationMapper thirdUserRelationMapper;

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public BusUser saveUserAndRelation(BusUserAndRelationDto busUserAndRelationDto) {
        BusUser busUser = new BusUser();
        busUser.setUserName(busUserAndRelationDto.getUserName());
        busUser.setPhoneNumber(busUserAndRelationDto.getPhoneNumber());
        busUser.setPassword(Md5Util.cryptMD5(busUserAndRelationDto.getPassword()));
        busUser.setCreateTime(new Date());
        busUser.setQuestionId(1);
        try {
            this.baseMapper.insert(busUser);
        } catch (Exception e) {
            log.error("busUser保存失败,{}", busUser);
            throw new ServiceException(500, "注册失败");
        }
        ThirdUserRelation thirdUserRelation = new ThirdUserRelation();
        thirdUserRelation.setOpenId(busUserAndRelationDto.getOpenId());
        thirdUserRelation.setUserId(busUser.getId());
        thirdUserRelation.setUserType(0);
        thirdUserRelation.setStatus(1);
        thirdUserRelation.setOpenIdPlatform("0");
        try {
            thirdUserRelationMapper.insert(thirdUserRelation);
        } catch (Exception e) {
            log.error("thirdUserRelation保存失败,{}", thirdUserRelation);
            throw new ServiceException(500, "注册失败");
        }
        return busUser;
    }

    @Override
    public BusUser getByPhoneNumber(String phoneNumber) {
        if (ToolUtil.isEmpty(phoneNumber)) {
            return null;
        }
        QueryWrapper<BusUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BusUser.COL_PHONE_NUMBER, phoneNumber).last("limit 1");
        return this.baseMapper.selectOne(queryWrapper);
    }

    @Resource
    public void setThirdUserRelationMapper(ThirdUserRelationMapper thirdUserRelationMapper) {
        this.thirdUserRelationMapper = thirdUserRelationMapper;
    }
}
