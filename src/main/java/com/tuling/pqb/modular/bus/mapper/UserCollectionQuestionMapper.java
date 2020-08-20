package com.tuling.pqb.modular.bus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuling.pqb.modular.bus.entity.UserCollectionQuestion;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserCollectionQuestionMapper extends BaseMapper<UserCollectionQuestion> {
    int updateBatch(List<UserCollectionQuestion> list);

    int batchInsert(@Param("list") List<UserCollectionQuestion> list);
}