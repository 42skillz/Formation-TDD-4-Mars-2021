import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringCalculatorBuilder {
    Supplier<Stream<Integer>> streamSupplier;

    public StringCalculatorBuilder() {
    }

    public StringCalculatorBuilder extractNumbers(String stringNumbers) {
        char delimiter = ',';

        if (stringNumbers.equals("")) stringNumbers = "0";

        if (this.hasCustomDelimiter(stringNumbers)) {
            delimiter = this.extractCustomDelimiter(stringNumbers);
            stringNumbers = this.subtractNewStringNumbers(stringNumbers);
        }
        Integer[] integers = Arrays.stream(stringNumbers.split(String.format("%c|\n", delimiter))).map(Integer::parseInt).toArray(Integer[]::new);
        this.streamSupplier = () -> Stream.of(integers);
        return this;
    }

    public StringCalculatorBuilder excludeNumberGreaterThan(int limit) {
        Integer[] integers = this.streamSupplier.get().filter(n -> n <= limit).toArray(Integer[]::new);
        this.streamSupplier = () -> Stream.of(integers);
        return this;
    }

    public StringCalculatorBuilder handleNegativesNumbers() throws Exception {
        if (this.getNegativesStream(streamSupplier).count() > 0) {
            this.throwNegativesNotAllowed();
        }
        return this;
    }

    public Integer sum() {
        return this.streamSupplier.get().reduce(Integer::sum).orElse(0);
    }

    private String subtractNewStringNumbers(String stringNumbers) {
        return stringNumbers.substring(4);
    }

    private char extractCustomDelimiter(String stringNumbers) {
        return stringNumbers.toCharArray()[2];
    }

    private boolean hasCustomDelimiter(String stringNumbers) {
        return stringNumbers.startsWith("//");
    }

    private void throwNegativesNotAllowed() throws Exception {
        throw new Exception(String.format("negatives not allowed:%s",
                getNegativesStream(this.streamSupplier).map(Object::toString).collect(Collectors.joining(","))));
    }

    private Stream<Integer> getNegativesStream(Supplier<Stream<Integer>> streamIntegers) {
        return streamIntegers.get().filter(this::isNegative);
    }

    private boolean isNegative(int number) {
        return number < 0;
    }
}