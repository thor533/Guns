package com.tuling.pqb.modular.bus.service.impl;

import com.tuling.pqb.modular.bus.service.ThirdUserRelationService;
import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuling.pqb.modular.bus.mapper.ThirdUserRelationMapper;
import com.tuling.pqb.modular.bus.entity.ThirdUserRelation;

@Service
public class ThirdUserRelationServiceImpl extends ServiceImpl<ThirdUserRelationMapper, ThirdUserRelation> implements ThirdUserRelationService {

    @Override
    public int updateBatch(List<ThirdUserRelation> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<ThirdUserRelation> list) {
        return baseMapper.batchInsert(list);
    }
}
