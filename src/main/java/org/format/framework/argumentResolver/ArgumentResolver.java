package org.format.framework.argumentResolver;

import org.format.framework.bind.DataBinder;
import org.format.framework.code.MethodParameter;

import javax.servlet.http.HttpServletRequest;

public interface ArgumentResolver {

    /**
     * 参数支持的类型
     * @param parameter
     * @return
     */
    boolean supportParameter(MethodParameter parameter);

    /**
     * 解析参数的逻辑
     * @param parameter
     * @param paramName 参数名
     * @param request
     * @param dataBinder 数据绑定器
     * @return
     */
    Object resolveArgument(MethodParameter parameter, String paramName, HttpServletRequest request, DataBinder dataBinder);

}
