package tech.vineyard.service;

import tech.vineyard.service.model.Input;

import java.lang.reflect.Method;

public class ReflectionService extends AbstractService {
    @Override
    public Object executeMethod(Input input) throws Exception {
        final Method method = input.current()
                .klass()
                .getMethod(input.methodName(), getParamClasses(input));

        return method.invoke(getParameterObject(input.current()), getParamObjects(input));
    }
}