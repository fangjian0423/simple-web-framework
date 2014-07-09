package org.format.framework.propertyeditor;

import org.format.framework.propertyeditor.editors.CustomBooleanEditor;
import org.format.framework.propertyeditor.editors.CustomNumberEditor;
import org.format.framework.util.ClassUtil;
import org.format.framework.util.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.beans.PropertyEditor;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class PropertyEditorRegistry {

    private String[] configFiles;

    private Map<Class<?>, PropertyEditor> defaultEditors;

    private Map<Class<?>, PropertyEditor> customEditors;

    private Map<Class<?>, PropertyEditor> customEditorCache;

    public PropertyEditorRegistry() {
        this.configFiles = new String[1];
        configFiles[0] = "propertyEditor/customBinders.xml";
        registerDefaultPropertyEditors();
        initCustomPropertyEditors();
    }

    private void initCustomPropertyEditors() {
        /**
         * 这里初始化的自定义属性编辑器都是需要有没有参数的构造函数的，否则请使用@Binder注解
         */
        this.customEditors = new HashMap<Class<?>, PropertyEditor>(64);
        try {
            initFormConfigFile();
            initFromAnnotation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 配置文件初始化自定义属性编辑器
     */
    private void initFormConfigFile() throws Exception {
        if(this.configFiles != null && this.configFiles.length > 0) {
            for(String configFile : configFiles) {
                Element doc = XmlUtil.buildDoc(configFile);
                List<Element> nodes = XmlUtil.getChildElementsByTagName(doc, "binder");
                for(Element node : nodes) {
                    Element classNode = XmlUtil.getChildElementByTagName(node, "class");
                    Element editorNode = XmlUtil.getChildElementByTagName(node ,"editor");
                    this.customEditors.put(Class.forName(classNode.getTextContent()), (PropertyEditor)ClassUtil.newInstance(Class.forName(editorNode.getTextContent())));
                }
            }
        }
    }

    /**
     * 注解初始化自定义属性编辑器
     */
    private void initFromAnnotation() {
        
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

    public PropertyEditor getPropertyEditor(Class clazz) {
        PropertyEditor pe = this.defaultEditors.get(clazz);
        if(pe == null) {
            pe = this.customEditors.get(clazz);
        }
        return pe;
    }



}
