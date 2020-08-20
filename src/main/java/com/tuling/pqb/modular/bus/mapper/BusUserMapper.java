package com.tuling.pqb.modular.bus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuling.pqb.modular.bus.entity.BusUser;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BusUserMapper extends BaseMapper<BusUser> {
    int updateBatch(List<BusUser> list);

    int batchInsert(@Param("list") List<BusUser> list);
}