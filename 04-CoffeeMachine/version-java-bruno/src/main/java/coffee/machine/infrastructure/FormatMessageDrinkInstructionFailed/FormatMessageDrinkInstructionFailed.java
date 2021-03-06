package coffee.machine.infrastructure.FormatMessageDrinkInstructionFailed;

import coffee.machine.domain.DrinkInstructionFailed.DrinkInstructionFailed;
import coffee.machine.domain.KindOfDrink;

import java.util.Map;

public abstract class FormatMessageDrinkInstructionFailed {

    static Map<KindOfDrink, String> kindOfDrinksToDrinkLabel = Map.of(
            KindOfDrink.TEA, "Tea",
            KindOfDrink.COFFEE, "Coffee",
            KindOfDrink.CHOCOLATE, "Chocolate",
            KindOfDrink.ORANGE_JUICE, "OrangeJuice"
    );

    public abstract String formatMessage(DrinkInstructionFailed drinkInstructionFailed);
}