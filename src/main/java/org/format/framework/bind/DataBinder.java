package org.format.framework.bind;

import org.format.framework.code.MethodParameter;

import java.beans.PropertyEditor;
import java.util.List;

public interface DataBinder {

    <T> T convertIfNecessary(Object value, Class<T> requiredType, MethodParameter methodParam);

    void bind(Object obj, List<MethodParameter> parameters);

    void addCustomPropertyEditor(Class clazz, PropertyEditor propertyEditor);

}
