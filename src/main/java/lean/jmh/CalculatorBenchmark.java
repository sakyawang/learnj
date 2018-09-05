package lean.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.stream.IntStream;

import static org.openjdk.jmh.annotations.Scope.Benchmark;

@State(Benchmark)
@BenchmarkMode(Mode.AverageTime)
public class CalculatorBenchmark {

    private int length = 100000000;

    private int[] numbers;

    private Calculator serialCalculator;

    private Calculator parallelCalculator;

    @Setup
    public void prepare() {
        numbers = IntStream.rangeClosed(1, length).toArray();
        serialCalculator = new SerialCalculator();
        parallelCalculator = new ParallelCalculator(Runtime.getRuntime().availableProcessors());
    }

    @TearDown
    public void shutdown() {
        serialCalculator.shutdown();
        parallelCalculator.shutdown();
    }

    @Benchmark
    public long serialCalculator() {
        return serialCalculator.sum(numbers);
    }

    /*@Benchmark
    public long parallelCalculator() {
        return parallelCalculator.sum(numbers);
    }*/

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(CalculatorBenchmark.class.getSimpleName())
                .forks(1)
                .warmupIterations(2)
                .measurementIterations(2)
                .build();

        new Runner(opt).run();
    }
}
