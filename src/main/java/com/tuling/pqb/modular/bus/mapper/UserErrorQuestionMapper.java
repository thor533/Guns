package com.tuling.pqb.modular.bus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuling.pqb.modular.bus.entity.UserErrorQuestion;
import java.util.List;

import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

public interface UserErrorQuestionMapper extends BaseMapper<UserErrorQuestion> {
    int updateBatch(List<UserErrorQuestion> list);

    int batchInsert(@Param("list") List<UserErrorQuestion> list);

    void removeBatch(@Param("userId") Integer userId,@Param("list") List<UserErrorQuestion> list);

}