public class StringCalculator {
    public static int add(String stringNumbers) throws Exception {
        return new StringCalculatorBuilder()
                .extractNumbers(stringNumbers)
                .handleNegativesNumbers()
                .excludeNumberGreaterThan(1000)
                .sum();
    }
}


