package tech.vineyard.service;

import org.openjdk.jmh.annotations.Benchmark;
import tech.vineyard.service.model.Input;
import tech.vineyard.service.model.Parameter;

import java.util.List;
import java.util.UUID;

public class ServiceBenchmark {
    private static final Service R_SERVICE = new ReflectionService();
    private static final Service MH_SERVICE = new ReflectionMHService();
    private static final Service LMF_SERVICE = new LambdaMetafactoryService<>();

    @Benchmark
    public void reflection() throws Throwable {
        runBenchmark(R_SERVICE);
    }

    @Benchmark
    public void reflectionMethodHandle() throws Throwable {
        runBenchmark(MH_SERVICE);
    }

    @Benchmark
    public void lambdaMetaFactoryHandle() throws Throwable {
        runBenchmark(LMF_SERVICE);
    }

    private void runBenchmark(Service service) throws Throwable {
        service.executeMethod(generateInput());
    }

    private Input generateInput() {
        return new Input(String.class, parameter(string()), "concat", List.of(parameter(string())));
    }

    private String string() {
        return UUID.randomUUID()
                .toString();
    }

    private Parameter parameter(String string) {
        return new Parameter(String.class, "\"" + string + "\"");
    }
}
