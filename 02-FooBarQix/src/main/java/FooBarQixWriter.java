import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FooBarQixWriter {
    final FooBarQix fooBarQix;

    public FooBarQixWriter() {
        this.fooBarQix = new FooBarQix();
    }

    public String Write(int from, int to) {
        final Stream<String> stringStream = IntStream.rangeClosed(from, to)
                .mapToObj(n -> String.format("%3d : %s", n, this.fooBarQix.generate(n)));
        return stringStream.collect(Collectors.joining("\n"));
    }
}