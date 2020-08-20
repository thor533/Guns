package com.tuling.pqb.modular.bus.service;

import com.tuling.pqb.modular.bus.dto.BusUserAndRelationDto;
import com.tuling.pqb.modular.bus.entity.BusUser;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
public interface BusUserService extends IService<BusUser>{


    int updateBatch(List<BusUser> list);

    int batchInsert(List<BusUser> list);

    BusUser saveUserAndRelation(BusUserAndRelationDto busUserAndRelationDto);

    /**
     * 手机号获取
     * @param phoneNumber
     * @return
     */
    BusUser getByPhoneNumber(String phoneNumber);
}
