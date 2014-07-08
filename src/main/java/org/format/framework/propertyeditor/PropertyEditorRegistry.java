package org.format.framework.propertyeditor;

import org.format.framework.propertyeditor.editors.CustomBooleanEditor;
import org.format.framework.propertyeditor.editors.CustomNumberEditor;

import java.beans.PropertyEditor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class PropertyEditorRegistry {

    private Map<Class<?>, PropertyEditor> defaultEditors;

    private Map<Class<?>, PropertyEditor> customEditors;

    private Map<Class<?>, PropertyEditor> customEditorCache;

    public PropertyEditorRegistry() {
        registerDefaultPropertyEditors();
        initCustomPropertyEditors();
    }

    private void initCustomPropertyEditors() {
    }

    private void registerDefaultPropertyEditors() {
        this.defaultEditors = new HashMap<Class<?>, PropertyEditor>(64);

        //boolean editor
        this.defaultEditors.put(boolean.class, new CustomBooleanEditor(false));
        this.defaultEditors.put(Boolean.class, new CustomBooleanEditor(true));

        //number editor
        this.defaultEditors.put(byte.class, new CustomNumberEditor(Byte.class, false));
        this.defaultEditors.put(Byte.class, new CustomNumberEditor(Byte.class, true));
        this.defaultEditors.put(short.class, new CustomNumberEditor(Short.class, false));
        this.defaultEditors.put(Short.class, new CustomNumberEditor(Short.class, true));
        this.defaultEditors.put(int.class, new CustomNumberEditor(Integer.class, false));
        this.defaultEditors.put(Integer.class, new CustomNumberEditor(Integer.class, true));
        this.defaultEditors.put(long.class, new CustomNumberEditor(Long.class, false));
        this.defaultEditors.put(Long.class, new CustomNumberEditor(Long.class, true));
        this.defaultEditors.put(float.class, new CustomNumberEditor(Float.class, false));
        this.defaultEditors.put(Float.class, new CustomNumberEditor(Float.class, true));
        this.defaultEditors.put(double.class, new CustomNumberEditor(Double.class, false));
        this.defaultEditors.put(Double.class, new CustomNumberEditor(Double.class, true));
        this.defaultEditors.put(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, true));
        this.defaultEditors.put(BigInteger.class, new CustomNumberEditor(BigInteger.class, true));
    }

    public PropertyEditor getDefaultPropertyEditor(Class clazz) {
        return this.defaultEditors.get(clazz);
    }



}
