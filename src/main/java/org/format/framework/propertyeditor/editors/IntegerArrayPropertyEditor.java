package org.format.framework.propertyeditor.editors;

import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyEditorSupport;

public class IntegerArrayPropertyEditor extends PropertyEditorSupport {

    private String separator;

    public IntegerArrayPropertyEditor(String separator) {
        this.separator = separator;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if(StringUtils.isBlank(text)) {
            setValue(null);
            return;
        }
        if(text.contains(separator)) {
            String[] arr = text.split(separator);
            int[] result = new int[arr.length];
            for(int i = 0; i < arr.length; i ++) {
                String str = arr[i];
                result[i] = Integer.parseInt(str);
            }
            setValue(result);
        } else {
            this.setValue(new int[] {Integer.parseInt(text)});
        }
    }

}
