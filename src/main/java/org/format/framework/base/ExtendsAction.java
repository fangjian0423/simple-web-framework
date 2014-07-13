package org.format.framework.base;

import com.opensymphony.xwork2.ActionSupport;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.format.framework.annotation.Action;
import org.format.framework.test.TestAction;
import org.format.framework.util.ClassUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 抽象类，继承自ActionSupport
 */
public abstract class ExtendsAction extends ActionSupport {

    private static Log log = LogFactory.getLog(ExtendsAction.class);

    public static final String ACTION_METHOD = "act";

    protected HttpServletRequest req = null;
    protected HttpServletResponse resp = null;

    protected String pageLocation = null;

    @Override
    public String execute() throws Exception {

        init();

        req = getRequest();
        resp = getResponse();

        String methodName = req.getParameter(ACTION_METHOD);

        if(StringUtils.isBlank(methodName)) {
            throw new RuntimeException("we need a methodName.");
        }

        String af = beforeExecute();
        if(af != null) {
            return af;
        }

        try {
            String forward = invokeMethod(methodName);
            if(log.isDebugEnabled()) {
                log.debug("methodName: " + methodName+", forward: " + forward);
            }
            return forward;
        } catch (Exception e) {
            String errorInfo = e.getMessage();
            req.setAttribute("msg", errorInfo);
            return ERROR;
        }

    }

    protected abstract void init();

    /**
     * 根据方法名找到对应的方法并invoke
     * @param methodName
     * @return
     */
    private String invokeMethod(String methodName) {

        Method doMethod = getInvokeMethod(methodName);

        if(doMethod == null) {
            throw new RuntimeException("can not find a method: " + methodName);
        }

        Object ret = null;

        Object[] args = resolveParameters(doMethod);

        try {
            ret = doMethod.invoke(this, (Object[])args);
            //FIXME 异常可以做点文章
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Class<?> retType = doMethod.getReturnType();
        if(retType != null && retType == String.class) {
            return (String) ret;
        } else if(retType == void.class) {
            return NONE;
        } else {
            return SUCCESS;
        }
    }

    /**
     * 解析参数(使用javassist解析)
     * @param doMethod
     * @return 各个参数的值
     */
    protected abstract Object[] resolveParameters(Method doMethod);

    /**
     * 找到要调用的方法
     * @param methodName
     * @return
     */
    private Method getInvokeMethod(String methodName) {
        List<Method> methods = getMethods();

        Method doMethod = null;

        for(Method method : methods) {
            if(method.getName().equals(methodName)) {
                doMethod = method;
                break;
            }
        }

        return doMethod;
    }

    /**
     * 找出所有的方法
     * @return
     */
    private List<Method> getMethods() {
        List<Method> methods = new ArrayList<Method>();
        recursion(this.getClass(), methods);
        return methods;
    }

    /**
     * 递归找出所有的方法
     * @param clazz
     * @param methods
     */
    private void recursion(Class clazz, List<Method> methods) {
        methods.addAll(Arrays.asList(clazz.getDeclaredMethods()));
        if(clazz.getSuperclass() != null) {
            recursion(clazz.getSuperclass(), methods);
        }
    }


    /**
     * 子类可重写该方法，进行一些额外的处理
     * @return
     */
    protected String beforeExecute() {
        return null;
    }

    public HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }

    public HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }



    protected String forward(String location) {
        Action anno = this.getClass().getAnnotation(Action.class);
        String prefix = null;
        String newLocation = null;
        if(anno != null) {
            prefix = anno.value().startsWith("/") ? anno.value() : ("/" + anno.value());
            if(prefix.endsWith("/")) {
                if(location.startsWith("/")) {
                    newLocation = prefix.substring(0, prefix.length() - 1) + location;
                } else {
                    newLocation = prefix + location;
                }
            } else {
                if(location.startsWith("/")) {
                    newLocation = prefix + location;
                } else {
                    newLocation = prefix + "/" + location;
                }
            }
        }
        setPageLocation(newLocation);
        return SUCCESS;
    }

    public String getPageLocation() {
        return pageLocation;
    }
    public void setPageLocation(String pageLocation) {
        this.pageLocation = pageLocation;
    }

}
