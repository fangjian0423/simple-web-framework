package org.format.framework.argumentResolver;

import org.format.framework.bind.DataBinder;
import org.format.framework.code.MethodParameter;
import org.format.framework.util.ClassUtil;

import javax.servlet.http.HttpServletRequest;

public class SimpleArgumentResolver implements ArgumentResolver {

    public boolean supportParameter(MethodParameter parameter) {
        return ClassUtil.isSimpleType(parameter.getType());
    }

    public Object resolveArgument(MethodParameter parameter, String paramName, HttpServletRequest request, DataBinder dataBinder) {
        Object arg = resolveName(parameter, paramName, request);

        if(dataBinder != null) {
            dataBinder.convertIfNecessary(arg, parameter.getType(), parameter);
        }

        return arg;
    }

    private Object resolveName(MethodParameter parameter, String paramName, HttpServletRequest request) {
        Object arg = null;
        String[] paramValues = request.getParameterValues(paramName);
        if (paramValues != null) {
            arg = paramValues.length == 1 ? paramValues[0] : paramValues;
        }
        return arg;
    }

}
