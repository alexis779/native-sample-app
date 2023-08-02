package tech.vineyard.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tech.vineyard.service.model.Input;
import tech.vineyard.service.model.Parameter;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServiceTest {
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @ParameterizedTest
    @MethodSource("inputs")
    public void testService(final Service service, final Input input, final Object expectedOutput) throws Throwable {
        assertEquals(expectedOutput, service.executeMethod(input));
    }

    private static Stream<Arguments> inputs() throws IOException {
        final Input trimInput = new Input(String.class, getParameter(" to be trimmed "), "trim", List.of());
        final String trimOutput = "to be trimmed";

        final Input concatInput = new Input(String.class, getParameter("prefix"), "concat", List.of(getParameter("suffix")));
        final String concatOutput = "prefixsuffix";

        final Input byteInput = new Input(byte.class, getParameter(256), "byteValue", List.of());
        final byte byteOutput = 0;

        final Input cmpInput = new Input(int.class, getParameter(255), "compareTo", List.of(getParameter(258)));
        final int cmpOutput = -1;


        final List<Service> services = List.of(
                new ReflectionService(),
                new ReflectionMHService(),
                new LambdaMetafactoryService<>());

        final List<Input> inputs = List.of(
                trimInput,
                byteInput,
                concatInput,
                cmpInput
        );

        final List<Object> outputs = List.of(
                trimOutput,
                byteOutput,
                concatOutput,
                cmpOutput
        );

        return services.stream()
                .flatMap(service -> IntStream.range(0, inputs.size())
                                        .mapToObj(i -> Arguments.of(service, inputs.get(i), outputs.get(i))));
    }

    private static Parameter getParameter(Object object) throws IOException {
        return new Parameter(object.getClass(), OBJECT_MAPPER.writeValueAsString(object));
    }
}
