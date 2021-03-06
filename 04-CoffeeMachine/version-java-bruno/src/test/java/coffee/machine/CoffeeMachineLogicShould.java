package coffee.machine;

import coffee.machine.domain.CoffeeMachineLogic;
import coffee.machine.domain.FinancialReport;
import coffee.machine.domain.KindOfDrink;
import coffee.machine.infrastructure.CustomerOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

public class CoffeeMachineLogicShould {
    @Test
    public void
    be_able_to_produce_Command_for_DrinkMaker_when_Customer_order_1_tea_with_1_sugar_if_received_enough_money_for_it() {
        CoffeeMachineLogic coffeeMachineLogic = new CoffeeMachineLogicBuilder()
                .build();

        assertThat(coffeeMachineLogic
                .makeCommand(new CustomerOrder("Tea", false, 1, 0.4)))
                .isEqualTo("T:1:0");
    }

    @Test
    public void
    be_able_to_produce_Command_for_the_DrinkMaker_when_Customer_order_1_chocolate_with_no_sugar_if_received_enough_money_for_it() {
        CoffeeMachineLogic coffeeMachineLogic = new CoffeeMachineLogicBuilder()
                .build();

        assertThat(coffeeMachineLogic
                .makeCommand(new CustomerOrder("Chocolate", false, 0, 0.5)))
                .isEqualTo("H::");
    }

    @Test
    public void produce_error_message_when_given_drink_dont_supported_by_the_coffee_machine() {
        CoffeeMachineLogic coffeeMachineLogic = new CoffeeMachineLogicBuilder()
                .build();

        assertThat(coffeeMachineLogic
                .makeCommand(new CustomerOrder("Chocolate Mocha", false, 0, 0.5)))
                .isEqualTo("M:Drink Chocolate Mocha not supported");
    }

    @Test
    public void
    be_able_to_produce_Command_for_DrinkMaker_when_Customer_order_1_coffee_with_2_sugars__if_received_enough_money_for_it() {
        CoffeeMachineLogic coffeeMachineLogic = new CoffeeMachineLogicBuilder()
                .build();
        assertThat(coffeeMachineLogic.makeCommand(new CustomerOrder("Coffee", false, 2, 0.6)))
                .isEqualTo("C:2:0");
    }

    @ParameterizedTest
    @MethodSource("argWhenAmountOfMissingMoney")
    public void
    produce_order_with_amount_of_missing_money_to_the_DrinkMaker_when_no_money_has_been_received(String drink, double currencyMoney, double expected) {
        CoffeeMachineLogic coffeeMachineLogic = new CoffeeMachineLogicBuilder()
                .build();

        assertThat(coffeeMachineLogic
                .makeCommand(new CustomerOrder(drink, false, 2, currencyMoney)))
                .isEqualTo(String.format("M:Missing %.2f euro", expected));
    }

    @Test
    public void
    be_able_to_produce_Command_for_the_DrinkMaker_when_Customer_order_1_orange_juice_if_received_enough_money_for_it() {
        CoffeeMachineLogic coffeeMachineLogic = new CoffeeMachineLogicBuilder()
                .build();

        assertThat(coffeeMachineLogic
                .makeCommand(new CustomerOrder("OrangeJuice", false, 0, 0.6)))
                .isEqualTo("O::");
    }

    @ParameterizedTest
    @MethodSource("argWhenCustomerOrderOneExtraHotDrink")
    public void
    be_able_to_produce_Command_for_the_DrinkMaker_when_Customer_order_1_extra_hot_drink_if_received_enough_money_for_it(String drink, String commandExtraHot) {
        CoffeeMachineLogic coffeeMachineLogic = new CoffeeMachineLogicBuilder()
                .build();

        assertThat(coffeeMachineLogic
                .makeCommand(new CustomerOrder(drink, true, 0, 0.6)))
                .isEqualTo(String.format("%s::", commandExtraHot));
    }

    @Test
    public void never_ask_an_ExtraHot_OrangeJuice_to_the_DrinkMaker_whatever_the_initial_Order() {
        CoffeeMachineLogic coffeeMachineLogic = new CoffeeMachineLogicBuilder()
                .build();

        assertThat(coffeeMachineLogic
                .makeCommand(new CustomerOrder("OrangeJuice", true, 0, 0.6)))
                .isEqualTo("M:We can't deliver extra hot for OrangeJuice");

    }

    @Test
    public void
    report_how_many_of_drinks_was_sold_and_the_total_amount_of_money_earned() {

        FinancialReport financialReport = new FinancialReport();

        CoffeeMachineLogic coffeeMachineLogic = new CoffeeMachineLogicBuilder()
                .withFinancialReport(financialReport)
                .build();

        assertThatBeverageCountForDrink(financialReport, coffeeMachineLogic, 2, "Tea", 2, KindOfDrink.TEA);
        assertThatBeverageCountForDrink(financialReport, coffeeMachineLogic, 1, "Chocolate", 0, KindOfDrink.CHOCOLATE);
        assertThatBeverageCountForDrink(financialReport, coffeeMachineLogic, 3, "OrangeJuice", 0, KindOfDrink.ORANGE_JUICE);
        assertThatBeverageCountForDrink(financialReport, coffeeMachineLogic, 4, "Coffee", 0, KindOfDrink.COFFEE);

        assertThat(financialReport.totalBeverageSold()).isEqualTo(10);
        assertThat(financialReport.totalAmount()).isEqualTo(5.5);
    }

    @Test
    public void
    notify_via_a_message_but_also_notify_us_via_eMail_when_shortage_is_detected() {
        CoffeeMachineLogicBuilder coffeeMachineLogicBuilder = new CoffeeMachineLogicBuilder();

        CoffeeMachineLogic coffeeMachineLogic = coffeeMachineLogicBuilder
                .withBeverageQuantityChecker("Chocolate", true)
                .withEMailNotifier()
                .build();

        assertThat(coffeeMachineLogic
                .makeCommand(new CustomerOrder("Chocolate", true, 1, 0.6)))
                .isEqualTo("M:Chocolate shortage (a notification has been sent to our logistic division). Please pick another option.");

        verify(coffeeMachineLogicBuilder.getEMailNotifier(), Mockito.times(1))
                .notifyMissingDrink("CHOCOLATE");
    }

    private static Stream<Arguments> argWhenAmountOfMissingMoney() {
        return Stream.of(Arguments.of("Coffee", 0.5, 0.10),
                Arguments.of("Chocolate", 0.2, 0.30),
                Arguments.of("Tea", 0.2, 0.2)
        );
    }

    private static Stream<Arguments> argWhenCustomerOrderOneExtraHotDrink() {
        return Stream.of(Arguments.of("Coffee", "Ch"),
                Arguments.of("Chocolate", "Hh"),
                Arguments.of("Tea", "Th")
        );
    }

    private void assertThatBeverageCountForDrink(FinancialReport financialReport, CoffeeMachineLogic coffeeMachineLogic, int times, String drink, int nbSugars, KindOfDrink kindOfDrink) {
        makeSeveralCommands(coffeeMachineLogic, times, drink, nbSugars);
        assertThat(financialReport.beverageCount(kindOfDrink)).isEqualTo(times);
    }

    private void makeSeveralCommands(CoffeeMachineLogic coffeeMachineLogic, int times, String drink, int nbSugars) {
        for (int idx = 0; idx < times; idx++) {
            coffeeMachineLogic.makeCommand(new CustomerOrder(drink, false, nbSugars, 0.6));
        }
    }
}