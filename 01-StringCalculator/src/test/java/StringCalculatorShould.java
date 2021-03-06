import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class StringCalculatorShould {
    @Test
    public void return_zero_when_string_number_is_empty() throws Exception {
        assertThat(StringCalculator.add("")).isEqualTo(0);
    }

    static Stream<Arguments> argWhenGivenOneNumber()
    {
        return Stream.of(Arguments.of("1", 1), Arguments.of("2", 2));
    }

    @ParameterizedTest
    @MethodSource("argWhenGivenOneNumber")
    public void return_number_when_string_number_contains_one_number(String numbers, int expected) throws Exception {
        assertThat(StringCalculator.add(numbers)).isEqualTo(expected);
    }

    @Test
    public void add_numbers_when_string_number_contains_two_number() throws Exception {
        assertThat(StringCalculator.add("1,2")).isEqualTo(3);
    }
    static Stream<Arguments> argWhenStringNumbersContainsUnknownAmountOfNumber() {
        return Stream.of(Arguments.of("1,2,3", 6), Arguments.of("1,2,3,4", 10));
    }
    @ParameterizedTest
    @MethodSource("argWhenStringNumbersContainsUnknownAmountOfNumber")
    public void add_numbers_when_string_number_contains_unknown_amount_of_number(String stringNumbers, int expected) throws Exception {
        assertThat(StringCalculator.add(stringNumbers)).isEqualTo(expected);
    }

    @Test
    public void support_new_line_as_delimiter_when_add_numbers() throws Exception {
        assertThat(StringCalculator.add("1\n2,3")).isEqualTo(6);
    }

    @Test
    public void support_different_delimiters_when_add_numbers() throws Exception {
        assertThat(StringCalculator.add("//;\n1;2;3")).isEqualTo(6);
    }

    @Test
    public void raise_exception_when_string_number_contains_negatives_numbers() {
        assertThatThrownBy(() -> StringCalculator.add("1,-2,3,-4"))
                .isInstanceOf(Exception.class)
                .hasMessage("negatives not allowed:-2,-4");
    }

    @Test
    public void ignored_numbers_when_they_are_bigger_than_1000() throws Exception {
        assertThat(StringCalculator.add("1001,2")).isEqualTo(2);
    }
}
