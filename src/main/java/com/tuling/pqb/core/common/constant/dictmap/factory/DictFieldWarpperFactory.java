
package com.tuling.pqb.core.common.constant.dictmap.factory;

import com.tuling.pqb.core.common.constant.factory.ConstantFactory;
import com.tuling.pqb.core.common.constant.factory.IConstantFactory;
import com.tuling.pqb.core.common.exception.BizExceptionEnum;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;

import java.lang.reflect.Method;

/**
 * 字典字段的包装器(从ConstantFactory中获取包装值)
 *
 * @author gaohan
 * @date 2017-05-06 15:12
 */
public class DictFieldWarpperFactory {

    public static Object createFieldWarpper(Object parameter, String methodName) {
        IConstantFactory constantFactory = ConstantFactory.me();
        try {
            Method method = IConstantFactory.class.getMethod(methodName, parameter.getClass());
            return method.invoke(constantFactory, parameter);
        } catch (Exception e) {
            try {
                Method method = IConstantFactory.class.getMethod(methodName, Long.class);
                return method.invoke(constantFactory, Long.parseLong(parameter.toString()));
            } catch (Exception e1) {
                throw new ServiceException(BizExceptionEnum.ERROR_WRAPPER_FIELD);
            }
        }
    }

}
