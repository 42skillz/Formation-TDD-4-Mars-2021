package coffee.machine.infrastructure.FormatMessageDrinkInstructionFailed;

import coffee.machine.domain.DrinkInstructionFailed.DrinkInstructionFailed;
import coffee.machine.domain.DrinkInstructionFailed.DrinkInstructionFailedBecauseDrinkNotSupported;

public class FormatMessageForDrinkNotSupported extends FormatMessageDrinkInstructionFailed {
    @Override
    public String formatMessage(DrinkInstructionFailed drinkInstructionFailed) {
        String drinkNameNotSupported = ((DrinkInstructionFailedBecauseDrinkNotSupported) drinkInstructionFailed).getDrinkNotSupported();
        return String.format("M:Drink %s not supported", drinkNameNotSupported);
    }
}



