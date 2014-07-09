package org.format.framework.bind;

import org.format.framework.code.MethodParameter;
import org.format.framework.propertyeditor.PropertyEditorRegistry;
import org.format.framework.util.ClassUtil;

import java.beans.PropertyEditor;
import java.lang.reflect.Method;
import java.util.List;

public class DefaultDataBinder extends PropertyEditorRegistry implements DataBinder {

    @Override
    public <T> T convertIfNecessary(Object value, Class<T> requiredType, MethodParameter methodParam) {
        PropertyEditor pe = getDefaultPropertyEditor(requiredType);
        try {
            if(pe == null) {
                //以String类型的构造函数进行实例化
                return ClassUtil.instantiateClass(requiredType.getConstructor(String.class), value.toString());
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        pe.setAsText(value.toString());
        return (T) pe.getValue();
    }

    @Override
    public void bind(Object obj, List<MethodParameter> paramters) {

        for(MethodParameter parameter : paramters) {
            if(parameter.getParamName().contains(".")) {
                //多级属性暂时先不做
            } else {
                Method setMethod = ClassUtil.getSetMethods(obj.getClass(), parameter.getParamName());
                try {
                    if(setMethod != null) {
                        Object convertval = null;
                        String[] values = (String[])parameter.getValue();
                        if(values.length == 1) {
                            PropertyEditor pe = getDefaultPropertyEditor(parameter.getType());
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
