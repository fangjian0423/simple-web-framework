package org.format.framework.code;

/**
 * 封装了方法参数的类
 */
public class MethodParameter {

    private String paramName;

    private int index;

    private Class type;

    public MethodParameter() {
    }

    public MethodParameter(String paramName, Class type, int index) {
        this.paramName = paramName;
        this.type = type;
        this.index = index;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

}
