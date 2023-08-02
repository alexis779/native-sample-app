package tech.vineyard.trim;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class TrimTest {
    private static final String INPUT = " to be trimmed ";
    private static final String OUTPUT = "to be trimmed";

    @ParameterizedTest
    @MethodSource("inputs")
    public void testTrim(final Trim trim) {
        Assertions.assertEquals(OUTPUT, trim.trim(INPUT));
    }

    private static Stream<Trim> inputs() {
        return Stream.of(
                new DirectTrim(),
                new MethodTrim(),
                new MethodHandleTrim(),
                new LambdaTrim(),
                new LambdaMetafactoryTrim()
        );
    }
}
