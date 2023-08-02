package tech.vineyard.trim;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandleTrim implements Trim {

    protected final MethodHandle methodHandle;

    public MethodHandleTrim() {
        final MethodHandles.Lookup lookup = MethodHandles.lookup();
        final MethodType methodType = MethodType.methodType(String.class);
        try {
            methodHandle = lookup.findVirtual(String.class, "trim", methodType);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String trim(final String input) {
        try {
            return (String) methodHandle.invoke(input);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
