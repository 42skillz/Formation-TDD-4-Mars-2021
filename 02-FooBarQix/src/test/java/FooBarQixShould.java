import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class FooBarQixShould {

    @ParameterizedTest
    @CsvSource(value = {"1:1", "2:2", "4:4"}, delimiter = ':')
    public void return_number_when_number_is_regular(String regularNumber, String expected) {
        assertThat(new FooBarQix()
                .generate(Integer.parseInt(regularNumber)))
                    .isEqualTo(expected);
    }

    @Test
    public void return_FooFoo_when_number_is_divisible_by_3_and_contains_3() {
        assertThat(new FooBarQix()
                .generate(3))
                    .isEqualTo("FooFoo");
    }

    @Test
    public void return_FooFooFoo_when_number_is_divisible_by_3_and_contains_3_twice() {
        assertThat(new FooBarQix()
                .generate(33))
                    .isEqualTo("FooFooFoo");
    }

    @Test
    public void return_BarBar_when_number_is_divisible_by_5_and_contains_5() {
        assertThat(new FooBarQix()
                .generate(5))
                    .isEqualTo("BarBar");
    }

    @Test
    public void return_QixQix_when_number_is_divisible_by_7_and_contains_7() {
        assertThat(new FooBarQix()
                .generate(7))
                    .isEqualTo("QixQix");
    }

    @Test
    public void return_FooBarBar_when_number_is_divisible_by_3_and_5() {
        assertThat(new FooBarQix()
                .generate(15))
                    .isEqualTo("FooBarBar");
    }

    @Test
    public void return_FooBar_when_number_is_divisible_by_3_and_contains_5() {
        assertThat(new FooBarQix()
                .generate(51))
                .isEqualTo("FooBar");
    }

    @Test
    public void support_other_symbols_without_changing_the_code() {
        assertThat(new FooBarQix(new HashMap<>() {{put(3, "Fizz"); put(5, "Buzz"); put(7, "Oua"); }})
                .generate(51))
                .isEqualTo("FizzBuzz");
    }
}
