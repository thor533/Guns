package com.tuling.pqb.modular.bus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuling.pqb.modular.bus.entity.UserEverydayQuestion;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserEverydayQuestionMapper extends BaseMapper<UserEverydayQuestion> {
    int updateBatch(List<UserEverydayQuestion> list);

    int batchInsert(@Param("list") List<UserEverydayQuestion> list);
}