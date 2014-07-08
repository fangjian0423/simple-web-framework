package org.format.framework.bind;

import org.format.framework.code.MethodParameter;
import org.format.framework.propertyeditor.PropertyEditorRegistry;
import org.format.framework.util.ClassUtil;

import java.beans.PropertyEditor;

public class DefaultDataBinder implements DataBinder {

    private PropertyEditorRegistry per = new PropertyEditorRegistry();

    @Override
    public <T> T convertIfNecessary(Object value, Class<T> requiredType, MethodParameter methodParam) {
        PropertyEditor pe = per.getDefaultPropertyEditor(requiredType);
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

}
