public class StringCalculator {
    public int add(String stringNumbers) throws Exception {

        return new StringCalculatorBuilder()
                .extractNumbers(stringNumbers)
                .handleNegativesNumbers()
                .excludeNumberGreaterThan(1000)
                .sum();
    }
}


