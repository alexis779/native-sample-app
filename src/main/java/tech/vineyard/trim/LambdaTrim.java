package tech.vineyard.trim;

import java.util.function.Supplier;

public class LambdaTrim implements Trim {
    @Override
    public String trim(final String input) {
        final Supplier<String> supplier = input::trim;
        return supplier.get();
    }
}
