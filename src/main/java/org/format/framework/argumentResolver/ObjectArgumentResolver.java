package org.format.framework.argumentResolver;

import org.format.framework.annotation.Obj;
import org.format.framework.bind.DataBinder;
import org.format.framework.code.MethodParameter;
import org.format.framework.util.ClassUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class ObjectArgumentResolver implements ArgumentResolver {


    @Override
    public boolean supportParameter(MethodParameter parameter) {
        return parameter.hasAnnotation(Obj.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, String paramName, HttpServletRequest request, DataBinder dataBinder) {
        Object obj = ClassUtil.newInstance(parameter.getType());
        Obj annotation = (Obj)parameter.getAnnotation(Obj.class);

        String alias = annotation.value();

        bindParameters(obj, parameter, properties(alias, request), dataBinder);

        return obj;
    }

    private void bindParameters(Object obj, MethodParameter parameter, Map<String, String[]> params, DataBinder dataBinder) {
        Iterator<String> iter = params.keySet().iterator();
        List<MethodParameter> parameters = new ArrayList<MethodParameter>();
        try {
            while(iter.hasNext()) {
                String key = iter.next();
                MethodParameter[] methodParameters = ClassUtil.getParametersByMethod(parameter.getType(), ClassUtil.getSetMethods(parameter.getType(), key));
                if(methodParameters.length > 1) {
                    throw new IllegalArgumentException("set method's param's num is large than 1.");
                }
                if(methodParameters != null) {
                    methodParameters[0].setValue(params.get(key));
                    parameters.add(methodParameters[0]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        dataBinder.bind(obj, parameters);
    }

    private Map<String, String[]> properties(String alias, HttpServletRequest request) {
        Map<String, String[]> result = new HashMap<String, String[]>();
        Map<String, String[]> paramMap = request.getParameterMap();
        Iterator<String> iter = paramMap.keySet().iterator();
        while(iter.hasNext()) {
            String key = iter.next();
            String[] values = paramMap.get(key);
            if(key.startsWith(alias+".")) {
                result.put(key.substring(alias.length()+1), values);
            }
        }
        return result;
    }

}
