package org.format.framework.propertyeditor.editors;


import org.format.framework.test.Dept;

import java.beans.PropertyEditorSupport;

public class DeptPropertyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if(text.indexOf(",") > 0) {
            String[] array = text.split(",");
            Dept dept = new Dept(Integer.parseInt(array[0]), array[1]);
            setValue(dept);
        } else {
            throw new IllegalArgumentException("dept param is error");
        }
    }

}
