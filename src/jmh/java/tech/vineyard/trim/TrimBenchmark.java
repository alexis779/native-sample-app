package tech.vineyard.trim;

import org.openjdk.jmh.annotations.Benchmark;

import java.util.UUID;

public class TrimBenchmark {
    private static final Trim DIRECT_TRIM = new DirectTrim();
    private static final Trim METHOD_TRIM = new MethodTrim();
    private static final Trim METHOD_HANDLE_TRIM = new MethodHandleTrim();
    private static final Trim LAMBDA_TRIM = new LambdaTrim();
    private static final Trim LMF_TRIM = new LambdaMetafactoryTrim();

    @Benchmark
    public void direct() {
        trim(DIRECT_TRIM);
    }

    @Benchmark
    public void method() {
        trim(METHOD_TRIM);
    }

    @Benchmark
    public void methodHandle() {
        trim(METHOD_HANDLE_TRIM);
    }

    @Benchmark
    public void lambda() {
        trim(LAMBDA_TRIM);
    }

    @Benchmark
    public void lambdaMetaFactory() {
        trim(LMF_TRIM);
    }

    public void trim(Trim trim) {
        trim.trim(string());
    }

    private String string() {
        return UUID.randomUUID()
                .toString();
    }
}
