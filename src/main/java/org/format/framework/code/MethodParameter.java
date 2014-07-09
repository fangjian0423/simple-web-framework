package org.format.framework.code;

import java.lang.annotation.Annotation;

/**
 * 封装了方法参数的类
 */
public class MethodParameter {

    private String paramName;

    private int index;

    private Class type;

    private Annotation[] annotations;

    private Object value;

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

    public Annotation[] getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Annotation[] annotations) {
        this.annotations = annotations;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean hasAnnotation(Class clazz) {
        if(this.annotations != null && this.annotations.length > 0) {
            for(Annotation annotation : annotations) {
                if(annotation.annotationType().equals(clazz)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Annotation getAnnotation(Class clazz) {
        if(this.annotations != null && this.annotations.length > 0) {
            for(Annotation annotation : annotations) {
                if(annotation.annotationType().equals(clazz)) {
                    return annotation;
                }
            }
        }
        return null;
    }

}
