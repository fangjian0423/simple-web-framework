package org.format.framework.propertyeditor.binder;


import org.format.framework.bind.DataBinder;
import org.format.framework.propertyeditor.CustomBinder;
import org.format.framework.propertyeditor.editors.CustomDateEditor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GlobalCustomBinder implements CustomBinder {

    @Override
    public void addCustomPropertyEditor(DataBinder dataBinder) {
        dataBinder.addCustomPropertyEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

}
