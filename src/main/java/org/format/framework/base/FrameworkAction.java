package org.format.framework.base;

import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.format.framework.argumentResolver.ArgumentResolverComposite;
import org.format.framework.argumentResolver.SimpleArgumentResolver;
import org.format.framework.bind.DataBinder;
import org.format.framework.bind.DefaultDataBinder;
import org.format.framework.code.MethodParameter;

import java.lang.reflect.Method;

/**
 * 该类主要处理参数
 */
public class FrameworkAction extends ExtendsAction {

    private DataBinder dataBinder = new DefaultDataBinder();

    private ArgumentResolverComposite argumentResolvers;

    @Override
    protected void init() {
        argumentResolvers = new ArgumentResolverComposite();
        argumentResolvers.addResolver(new SimpleArgumentResolver());
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
            ClassPool pool = ClassPool.getDefault();
            pool.insertClassPath(new ClassClassPath(FrameworkAction.class));
            CtClass cc = pool.get(this.getClass().getName());
            CtMethod method = cc.getDeclaredMethod(doMethod.getName());

            MethodInfo methodInfo = method.getMethodInfo();
            CodeAttribute codeAttr = methodInfo.getCodeAttribute();

            LocalVariableAttribute attr = (LocalVariableAttribute) codeAttr.getAttribute(LocalVariableAttribute.tag);

            MethodParameter[] params = new MethodParameter[method.getParameterTypes().length];

            int pos = Modifier.isStatic(method.getModifiers()) ? 0 : 1;

            CtClass[] types = method.getParameterTypes();

            for (int i = 0; i < params.length; i++) {

                Class paramType = null;

                if(types[i].getName().equals("int")) {
                    paramType = int.class;
                } else if(types[i].getName().equals("short")) {
                    paramType = short.class;
                } else if(types[i].getName().equals("long")) {
                    paramType = long.class;
                } else if(types[i].getName().equals("float")) {
                    paramType = float.class;
                } else if(types[i].getName().equals("double")) {
                    paramType = double.class;
                } else if(types[i].getName().equals("char")) {
                    paramType = char.class;
                } else if(types[i].getName().equals("byte")) {
                    paramType = byte.class;
                } else {
                    paramType = Class.forName(types[i].getName());
                }

                params[i] = new MethodParameter(attr.variableName(i + pos),
                                                paramType,
                                                i);
            }
            return params;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
