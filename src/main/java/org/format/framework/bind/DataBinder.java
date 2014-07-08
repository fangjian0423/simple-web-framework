package org.format.framework.bind;

import org.format.framework.code.MethodParameter;

public interface DataBinder {

    <T> T convertIfNecessary(Object value, Class<T> requiredType, MethodParameter methodParam);

}
