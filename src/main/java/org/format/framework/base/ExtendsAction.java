package org.format.framework.base;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExtendsAction extends ActionSupport {

    private static Log log = LogFactory.getLog(ExtendsAction.class);

    public static final String ACTION_METHOD = "act";

    protected HttpServletRequest req = null;
    protected HttpServletResponse resp = null;

    protected String pageLocation = null;

    @Override
    public String execute() throws Exception {
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

            //TODO 目前不支持参数，之后会加入参数处理，跟SpringMVC一样
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

    private String invokeMethod(String methodName) {
        List<Method> methods = getMethods();

        Method doMethod = null;

        for(Method method : methods) {
            if(method.getName().equals(methodName)) {
                doMethod = method;
                break;
            }
        }

        if(doMethod == null) {
            throw new RuntimeException("can not find a method: " + methodName);
        }

        Object ret = null;

        try {
            ret = doMethod.invoke(this);
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

    private List<Method> getMethods() {
        List<Method> methods = new ArrayList<Method>();
        recursion(this.getClass(), methods);
        return methods;
    }

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
        setPageLocation(location);
        return SUCCESS;
    }

    public String getPageLocation() {
        return pageLocation;
    }
    public void setPageLocation(String pageLocation) {
        this.pageLocation = pageLocation;
    }
}
