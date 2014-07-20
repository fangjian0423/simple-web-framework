package org.format.framework.base;

import org.format.framework.argumentResolver.ArgumentResolverComposite;
import org.format.framework.argumentResolver.ObjectArgumentResolver;
import org.format.framework.argumentResolver.SimpleArgumentResolver;
import org.format.framework.bind.DataBinder;
import org.format.framework.bind.DefaultDataBinder;
import org.format.framework.code.MethodParameter;
import org.format.framework.util.ClassUtil;

import java.lang.reflect.Method;

/**
 * 该类主要处理参数
 */
public class FrameworkAction extends ExtendsAction {

    private DataBinder dataBinder = new DefaultDataBinder();

    private ArgumentResolverComposite argumentResolvers = new ArgumentResolverComposite();

    @Override
    protected void init() {
        synchronized (argumentResolvers) {
            argumentResolvers.addResolver(new SimpleArgumentResolver());
            argumentResolvers.addResolver(new ObjectArgumentResolver());
        }
    }

    @Override
    protected Object[] resolveParameters(Method doMethod) {
        MethodParameter[] parameters = getParameters(doMethod);
        Object[] args = new Object[parameters.length];
        for(int i = 0; i < parameters.length; i ++) {
            MethodParameter parameter = parameters[i];
            if(argumentResolvers.supportParameter(parameter)) {
                args[i] = argumentResolvers.resolveArgument(parameter, parameter.getParamName(), req, dataBinder);
                continue;
            }
            if(args[i] == null) {
                String msg = "resolve params error";
                throw new IllegalStateException(msg);
            }
        }
        return args;
    }

    /**
     * 根据类的方法得到该方法中的参数名以及参数类型
     * @return
     */
    private MethodParameter[] getParameters(Method doMethod) {
        try {
            return ClassUtil.getParametersByMethod(this.getClass(), doMethod);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
