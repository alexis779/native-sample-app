package tech.vineyard.trim;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodTrim implements Trim {
    private final Method method;

    public MethodTrim() {
        try {
            method = String.class.getMethod("trim");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String trim(String input) {
        try {
            return (String) method.invoke(input);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
