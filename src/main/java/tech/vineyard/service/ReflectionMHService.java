package tech.vineyard.service;

import tech.vineyard.service.model.Input;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.stream.Stream;

public class ReflectionMHService extends AbstractService {
    @Override
    public Object executeMethod(Input input) throws Throwable {
        final MethodType methodType = MethodType.methodType(input.outputClass(), getParamClasses(input));

        final MethodHandles.Lookup lookup = MethodHandles.lookup();
        final MethodHandle methodHandle = lookup.findVirtual(input.current().klass(), input.methodName(), methodType);

        final Object[] args = Stream.concat(
                Stream.of(input.current()),
                input.parameters().stream())
            .map(this::getParameterObject)
            .toArray();

        return methodHandle.invokeWithArguments(args);
    }
}