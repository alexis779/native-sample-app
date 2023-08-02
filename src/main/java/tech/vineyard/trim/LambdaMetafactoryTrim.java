package tech.vineyard.trim;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaConversionException;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.function.Function;

public class LambdaMetafactoryTrim extends MethodHandleTrim {

    private final MethodHandle metaMethodHandle;

    public LambdaMetafactoryTrim() {
        super();

        CallSite callSite;

        try {
            callSite = getCallSite();
        } catch (IllegalAccessException | LambdaConversionException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        metaMethodHandle = callSite.getTarget();
    }

    private CallSite getCallSite() throws NoSuchMethodException, IllegalAccessException, LambdaConversionException {
        final MethodHandles.Lookup lookup = MethodHandles.lookup();
        return LambdaMetafactory.metafactory(
                lookup,
                "apply",
                MethodType.methodType(Function.class),
                MethodType.methodType(Object.class, Object.class),
                methodHandle,
                MethodType.methodType(String.class, String.class)
        );
    }

    @Override
    public String trim(final String input) {
        Object object;
        try {
            object = metaMethodHandle.invoke();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }

        final Function<String, String> function = (Function<String, String>) object;
        return function.apply(input);
    }
}
