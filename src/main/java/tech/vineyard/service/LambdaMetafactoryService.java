package tech.vineyard.service;

import tech.vineyard.service.model.Input;
import tech.vineyard.service.model.Parameter;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.function.BiFunction;
import java.util.function.Function;

public class LambdaMetafactoryService<R> extends AbstractService {
    @Override
    public R executeMethod(Input input) throws Throwable {
        final int arity = input.parameters().size();

        return switch (arity) {
            case 0 -> executeMethod0(input);
            case 1 -> executeMethod1(input);
            default -> throw new RuntimeException(String.format("%d arity not supported", arity));
        };
    }
    private R executeMethod0(Input input) throws Throwable {
        final Parameter current = input.current();

        final MethodType methodType = MethodType.methodType(input.outputClass());
        final MethodHandles.Lookup lookup = MethodHandles.lookup();
        final MethodHandle methodHandle = lookup.findVirtual(current.klass(), input.methodName(), methodType);
        final MethodType dynamicMethodType = MethodType.methodType(input.outputClass(), current.klass());

        final CallSite callSite = LambdaMetafactory.metafactory(
                        lookup,
                        "apply",
                        MethodType.methodType(Function.class),
                        MethodType.methodType(Object.class, Object.class),
                        methodHandle,
                        dynamicMethodType);

        final Function<Object, R> supplier = (Function<Object, R>) callSite.getTarget()
                .invoke();

        return supplier.apply(getParameterObject(current));
    }
    public R executeMethod1(Input input) throws Throwable {
        final Parameter current = input.current();
        final Parameter parameter = input.parameters().get(0);
        final MethodType methodType = MethodType.methodType(input.outputClass(), parameter.klass());

        final MethodHandles.Lookup lookup = MethodHandles.lookup();
        final MethodHandle methodHandle = lookup.findVirtual(current.klass(), input.methodName(), methodType);
        final MethodType dynamicMethodType = MethodType.methodType(input.outputClass(), current.klass(), parameter.klass());

        final CallSite callSite = LambdaMetafactory.metafactory(
                lookup,
                "apply",
                MethodType.methodType(BiFunction.class),
                MethodType.methodType(Object.class, Object.class, Object.class),
                methodHandle,
                dynamicMethodType
        );

        final BiFunction<Object, Object, R> biFunction = (BiFunction<Object, Object, R>) callSite.getTarget()
                .invoke();;
        return biFunction.apply(getParameterObject(current), getParameterObject(parameter));
    }
}
