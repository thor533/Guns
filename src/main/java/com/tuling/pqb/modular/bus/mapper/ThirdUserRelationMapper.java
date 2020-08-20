package com.tuling.pqb.modular.bus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuling.pqb.modular.bus.entity.ThirdUserRelation;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ThirdUserRelationMapper extends BaseMapper<ThirdUserRelation> {
    int updateBatch(List<ThirdUserRelation> list);

    int batchInsert(@Param("list") List<ThirdUserRelation> list);
}