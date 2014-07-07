package org.format.framework.argumentResolver;

import org.format.framework.bind.DataBinder;

import javax.servlet.http.HttpServletRequest;

public interface ArgumentResolver {

    /**
     * 参数支持的类型
     * @param type
     * @return
     */
    boolean supportParameter(Class type);

    /**
     * 解析参数的逻辑
     * @param paranName 参数名
     * @param request
     * @param dataBinder 数据绑定器
     * @return
     */
    Object resolveArgument(String paranName, HttpServletRequest request, DataBinder dataBinder);

}
