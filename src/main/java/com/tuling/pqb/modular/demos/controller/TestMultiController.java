
package com.tuling.pqb.modular.demos.controller;

import com.tuling.pqb.modular.demos.service.TestMultiDbService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.SuccessResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试多数据源回滚
 *
 * @author gaohan
 * @Date 2018/7/20 23:39
 */
@RestController
@RequestMapping("/multi")
public class TestMultiController extends BaseController {

    @Autowired
    private TestMultiDbService testMultiDbService;

    @RequestMapping("")
    public Object auth() {
        testMultiDbService.beginTest();
        return SuccessResponseData.success();
    }

}

