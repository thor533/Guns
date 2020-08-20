package com.tuling.pqb.modular.bus.service;

import java.util.List;
import com.tuling.pqb.modular.bus.entity.ThirdUserRelation;
import com.baomidou.mybatisplus.extension.service.IService;
public interface ThirdUserRelationService extends IService<ThirdUserRelation>{


    int updateBatch(List<ThirdUserRelation> list);

    int batchInsert(List<ThirdUserRelation> list);

}
