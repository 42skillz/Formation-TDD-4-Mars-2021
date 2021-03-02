import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringCalculatorBuilder {
    char DEFAULT_DELIMITER = ',';
    // Supplier needed for stream & exception
    Supplier<Stream<Integer>> integersSupplier;

    public StringCalculatorBuilder() {
    }

    public StringCalculatorBuilder extractNumbers(String stringNumbers) {
        char delimiter = DEFAULT_DELIMITER;
        if (stringNumbers.equals("")) stringNumbers = "0";

        if (this.hasCustomDelimiter(stringNumbers)) {
            delimiter = this.extractCustomDelimiter(stringNumbers);
            stringNumbers = this.subtractNewStringNumbers(stringNumbers);
        }
        Integer[] integers = splitStringNumbersWithDelimiterAndNewLineAsDelimiters(stringNumbers, delimiter)
                .map(Integer::parseInt).toArray(Integer[]::new);
        // Supplier needed for stream & exception
        this.integersSupplier = () -> Stream.of(integers);
        return this;
    }

    public StringCalculatorBuilder excludeNumberGreaterThan(int limit) {
        Integer[] integers = this.integersSupplier.get().filter(n -> n <= limit).toArray(Integer[]::new);
        // Supplier needed for stream & exception
        this.integersSupplier = () -> Stream.of(integers);
        return this;
    }

    public StringCalculatorBuilder handleNegativesNumbers() throws Exception {
        if (this.getNegativesNumbers(integersSupplier).count() > 0) {
            this.throwNegativesNotAllowed();
        }
        return this;
    }

    public Integer sum() {
        return this.integersSupplier.get().reduce(Integer::sum).orElse(0);
    }

    private Stream<String> splitStringNumbersWithDelimiterAndNewLineAsDelimiters(String stringNumbers, char delimiter) {
        return Arrays.stream(stringNumbers.split(String.format("%c|\n", delimiter)));
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
                getNegativesNumbers(this.integersSupplier).map(Object::toString).collect(Collectors.joining(","))));
    }

    private Stream<Integer> getNegativesNumbers(Supplier<Stream<Integer>> streamIntegers) {
        return streamIntegers.get().filter(this::isNegative);
    }

    private boolean isNegative(int number) {
        return number < 0;
    }
}