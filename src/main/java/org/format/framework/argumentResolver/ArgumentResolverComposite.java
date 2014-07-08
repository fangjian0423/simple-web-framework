package org.format.framework.argumentResolver;

import org.format.framework.bind.DataBinder;
import org.format.framework.code.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

public class ArgumentResolverComposite implements ArgumentResolver {

    private final List<ArgumentResolver> argumentResolvers = new LinkedList<ArgumentResolver>();

    @Override
    public boolean supportParameter(MethodParameter parameter) {
        return getArgumentResolver(parameter) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, String paramName, HttpServletRequest request, DataBinder dataBinder) {
        ArgumentResolver result = getArgumentResolver(parameter);
        return result.resolveArgument(parameter, paramName, request, dataBinder);
    }

    private ArgumentResolver getArgumentResolver(MethodParameter parameter) {
        ArgumentResolver result = null;
        for (ArgumentResolver methodArgumentResolver : this.argumentResolvers) {
            if (methodArgumentResolver.supportParameter(parameter)) {
                result = methodArgumentResolver;
                break;
            }
        }
        return result;
    }

    public void addResolver(ArgumentResolver argumentResolver) {
        this.argumentResolvers.add(argumentResolver);
    }

}
