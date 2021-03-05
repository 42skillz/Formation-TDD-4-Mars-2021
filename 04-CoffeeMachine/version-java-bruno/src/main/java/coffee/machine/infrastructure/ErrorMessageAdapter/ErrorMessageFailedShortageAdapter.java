package coffee.machine.infrastructure.ErrorMessageAdapter;

import coffee.machine.domain.DrinkInstructionFailed.DrinkInstructionFailed;

public class ErrorMessageFailedShortageAdapter extends ErrorMessageAdapter {
    private final DrinkInstructionFailed drinkInstructionFailed;

    public ErrorMessageFailedShortageAdapter(DrinkInstructionFailed drinkInstructionFailed) {
        this.drinkInstructionFailed = drinkInstructionFailed;
    }

    @Override
    public String formatMessage() {
        return String.format("M:%s shortage (a notification has been sent to our logistic division). Please pick another option.", this.kindOfDrinksToDrinkLabel.get(drinkInstructionFailed.getDrink()));
    }
}