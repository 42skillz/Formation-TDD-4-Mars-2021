package coffee.machine.infrastructure.FormatMessageDrinkInstructionFailed;

import coffee.machine.domain.DrinkInstructionFailed.DrinkInstructionFailed;

public class FormatMessageFailedShortageBeverage extends FormatMessageDrinkInstructionFailed {
    @Override
    public String formatMessage(DrinkInstructionFailed drinkInstructionFailed) {
        return String.format("M:%s shortage (a notification has been sent to our logistic division). Please pick another option.", kindOfDrinksToDrinkLabel.get(drinkInstructionFailed.getDrink()));
    }
}