package org.format.framework.util;

import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.Locale;

public class ClassUtil {

    public static boolean isSimpleType(Class clazz) {
        return clazz.isPrimitive() || clazz.isEnum() ||
                CharSequence.class.isAssignableFrom(clazz) ||
                Number.class.isAssignableFrom(clazz) ||
                Date.class.isAssignableFrom(clazz) ||
                Boolean.class.isAssignableFrom(clazz) ||
                clazz.equals(URI.class) || clazz.equals(URL.class) ||
                clazz.equals(Locale.class) || clazz.equals(Class.class);
    }

}
