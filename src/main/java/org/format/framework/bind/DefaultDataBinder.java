package org.format.framework.bind;

import org.format.framework.code.MethodParameter;
import org.format.framework.propertyeditor.PropertyEditorRegistry;
import org.format.framework.util.ClassUtil;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyEditor;
import java.lang.reflect.Method;
import java.util.List;

public class DefaultDataBinder extends PropertyEditorRegistry implements DataBinder {

    @Override
    public <T> T convertIfNecessary(Object value, Class<T> requiredType, MethodParameter methodParam) {
        try {
            if(value == null) {
                //基本类型仅仅支持int和boolean
                if(requiredType.isPrimitive()) {
                    if(requiredType == Integer.TYPE) {
                        return (T) new Integer(0);
                    } else if(requiredType == Boolean.TYPE) {
                        return (T) new Boolean(false);
                    }
                }
                return null;
            }
            PropertyEditor pe = getPropertyEditor(requiredType);
            if(pe == null) {
                //以String类型的构造函数进行实例化
                return ClassUtil.instantiateClass(requiredType.getConstructor(String.class), value.toString());
            }

            pe.setAsText(value.toString());
            return (T) pe.getValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void bind(Object obj, List<MethodParameter> paramters) {

        for(MethodParameter parameter : paramters) {
            if(parameter.getParamName().contains(".")) {
                //多级属性使用Spring的BeanWrapper处理
                BeanWrapper bw = new BeanWrapperImpl(obj);
                bw.setPropertyValue(parameter.getParamName(), parameter.getValue());
            } else {
                Method setMethod = ClassUtil.getSetMethods(obj.getClass(), parameter.getParamName());
                try {
                    if(setMethod != null) {
                        Object convertval = null;
                        String[] values = (String[])parameter.getValue();
                        if(values.length == 1) {
                            PropertyEditor pe = getPropertyEditor(parameter.getType());
                            if(pe == null) {
                                //以String类型的构造函数进行实例化
                                convertval = ClassUtil.instantiateClass(parameter.getType().getConstructor(String.class), values[0].toString());
                            } else {
                                pe.setAsText(values[0].toString());
                                convertval = pe.getValue();
                            }
                            setMethod.invoke(obj, convertval);
                        } else {
                            setMethod.invoke(obj, (String[])values);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
