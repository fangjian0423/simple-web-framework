package org.format.framework.propertyeditor.editors;

import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyEditorSupport;

public class CustomBooleanEditor extends PropertyEditorSupport {

    public static final String VALUE_TRUE = "true";
    public static final String VALUE_FALSE = "false";

    public static final String VALUE_ON = "on";
    public static final String VALUE_OFF = "off";

    public static final String VALUE_YES = "yes";
    public static final String VALUE_NO = "no";

    public static final String VALUE_1 = "1";
    public static final String VALUE_0 = "0";

    private final String trueString;

    private final String falseString;

    private final boolean allowEmpty;


    public CustomBooleanEditor(boolean allowEmpty) {
        this(null, null, allowEmpty);
    }

    public CustomBooleanEditor(String trueString, String falseString, boolean allowEmpty) {
        this.trueString = trueString;
        this.falseString = falseString;
        this.allowEmpty = allowEmpty;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        String input = (text != null ? text.trim() : null);
        if (this.allowEmpty && StringUtils.isBlank(text)) {
            setValue(null);
        }
        else if (this.trueString != null && input.equalsIgnoreCase(this.trueString)) {
            setValue(Boolean.TRUE);
        }
        else if (this.falseString != null && input.equalsIgnoreCase(this.falseString)) {
            setValue(Boolean.FALSE);
        }
        else if (this.trueString == null &&
                (input.equalsIgnoreCase(VALUE_TRUE) || input.equalsIgnoreCase(VALUE_ON) ||
                        input.equalsIgnoreCase(VALUE_YES) || input.equals(VALUE_1))) {
            setValue(Boolean.TRUE);
        }
        else if (this.falseString == null &&
                (input.equalsIgnoreCase(VALUE_FALSE) || input.equalsIgnoreCase(VALUE_OFF) ||
                        input.equalsIgnoreCase(VALUE_NO) || input.equals(VALUE_0))) {
            setValue(Boolean.FALSE);
        }
        else {
            throw new IllegalArgumentException("Invalid boolean value [" + text + "]");
        }
    }

    @Override
    public String getAsText() {
        if (Boolean.TRUE.equals(getValue())) {
            return (this.trueString != null ? this.trueString : VALUE_TRUE);
        }
        else if (Boolean.FALSE.equals(getValue())) {
            return (this.falseString != null ? this.falseString : VALUE_FALSE);
        }
        else {
            return "";
        }
    }

}
