package org.format.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface ExcelAnnotation{
    public String sheetName() default "图表1";
    public String titleName() default "";
    public int sort() default 0;
    /**工具默认已经*150**/
    public int width() default 20;
    public boolean isInt() default false;
    /**LEFT:0x1,CENTER:0x2(默认值),RIGHT:0x3**/
    public short align() default 0x2;
}
