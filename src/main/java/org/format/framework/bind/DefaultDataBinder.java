package org.format.framework.bind;

import org.format.framework.code.MethodParameter;
import org.format.framework.propertyeditor.PropertyEditorRegistry;

import java.beans.PropertyEditor;

public class DefaultDataBinder implements DataBinder {

    private PropertyEditorRegistry per = new PropertyEditorRegistry();

    @Override
    public <T> T convertIfNecessary(Object value, Class<T> requiredType, MethodParameter methodParam) {
        PropertyEditor pe = per.getDefaultPropertyEditor(requiredType);
        pe.setAsText(value.toString());
        return (T) pe.getValue();
    }

}
