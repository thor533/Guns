
package com.tuling.pqb.core.beetl;

import com.tuling.pqb.core.util.KaptchaUtil;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.tuling.pqb.core.common.constant.Const;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * beetl拓展配置,绑定一些工具类,方便在模板中直接调用
 *
 * @author gaohan
 * @Date 2018/2/22 21:03
 */
public class BeetlConfiguration extends BeetlGroupUtilConfiguration {

    @Override
    public void initOther() {

        //全局共享变量
        Map<String, Object> shared = new HashMap<>();
        shared.put("systemName", Const.DEFAULT_SYSTEM_NAME);
        shared.put("welcomeTip", Const.DEFAULT_WELCOME_TIP);
        groupTemplate.setSharedVars(shared);

        //全局共享方法
        groupTemplate.registerFunctionPackage("shiro", new ShiroExt());
        groupTemplate.registerFunctionPackage("tool", new ToolUtil());
        groupTemplate.registerFunctionPackage("kaptcha", new KaptchaUtil());
    }
}
