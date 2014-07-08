package org.format.framework.annotation;

/**
 * 自定义Binder注解
 */
public @interface CustomBinder {

    /** 需要转换的类 */
    Class clazz();

    /** 对应的属性编辑器 */
    Class editor();

}
