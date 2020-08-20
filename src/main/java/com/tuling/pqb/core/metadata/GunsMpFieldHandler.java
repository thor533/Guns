
package com.tuling.pqb.core.metadata;

import com.tuling.pqb.core.shiro.ShiroKit;
import cn.stylefeng.roses.core.metadata.CustomMetaObjectHandler;
import org.springframework.stereotype.Component;

/**
 * 字段填充器
 *
 * @author gaohan
 * @Date 2018/12/8 15:01
 */
@Component
public class GunsMpFieldHandler extends CustomMetaObjectHandler {

    @Override
    protected Object getUserUniqueId() {
        try {

            return ShiroKit.getUser().getId();

        } catch (Exception e) {

            //如果获取不到当前用户就存空id
            return "";
        }
    }
}
