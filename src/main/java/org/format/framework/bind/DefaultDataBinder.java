package org.format.framework.bind;

import org.format.framework.code.MethodParameter;

public class DefaultDataBinder implements DataBinder {

    @Override
    public <T> T convertIfNecessary(Object value, Class<T> requiredType, MethodParameter methodParam) {

        return null;
    }

}
