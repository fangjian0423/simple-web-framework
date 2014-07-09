package org.format.framework.util;

import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.format.framework.code.MethodParameter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.Locale;

public class ClassUtil {

    public static boolean isSimpleType(Class clazz) {
        return clazz.isPrimitive() || clazz.isEnum() ||
                CharSequence.class.isAssignableFrom(clazz) ||
                Number.class.isAssignableFrom(clazz) ||
                Date.class.isAssignableFrom(clazz) ||
                Boolean.class.isAssignableFrom(clazz) ||
                clazz.equals(URI.class) || clazz.equals(URL.class) ||
                clazz.equals(Locale.class) || clazz.equals(Class.class);
    }

    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T instantiateClass(Constructor<T> ctor, Object... args) {
        try {
            return ctor.newInstance(args);
        }
        catch (InstantiationException ex) {
            ex.printStackTrace();
        }
        catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static MethodParameter[] getParametersByMethod(Class clazz, Method doMethod) throws Exception {
        //所有参数的注解
        Annotation[][] paramAnnos = doMethod.getParameterAnnotations();

        //使用javassist获得参数的一些信息
        ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath(new ClassClassPath(ClassUtil.class));
        CtClass cc = pool.get(clazz.getName());
        CtMethod method = cc.getDeclaredMethod(doMethod.getName());

        MethodInfo methodInfo = method.getMethodInfo();
        CodeAttribute codeAttr = methodInfo.getCodeAttribute();

        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttr.getAttribute(LocalVariableAttribute.tag);

        MethodParameter[] params = new MethodParameter[method.getParameterTypes().length];

        int pos = Modifier.isStatic(method.getModifiers()) ? 0 : 1;

        CtClass[] types = method.getParameterTypes();

        for (int i = 0; i < params.length; i++) {
            //构造MethodParameter
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
            } else if(types[i].getName().equals("boolean")) {
                paramType = boolean.class;
            } else {
                paramType = Class.forName(types[i].getName());
            }

            params[i] = new MethodParameter(attr.variableName(i + pos),
                    paramType, i);

            params[i].setAnnotations(paramAnnos[i]);
        }
        return params;
    }


}
