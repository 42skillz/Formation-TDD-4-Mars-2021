package coffee.machine.infrastructure.FormatMessageDrinkInstructionFailed;

import coffee.machine.domain.DrinkInstructionFailed.DrinkInstructionFailed;

public class FormatMessageForIncompatibilityWithExtraHotFeature extends FormatMessageDrinkInstructionFailed {
    @Override
    public String formatMessage(DrinkInstructionFailed drinkInstructionFailed) {
        return String.format("M:We can't deliver extra hot for %s",
                kindOfDrinksToDrinkLabel.get(drinkInstructionFailed.getDrink()));
    }
}
