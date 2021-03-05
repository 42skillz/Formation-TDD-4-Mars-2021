package coffee.machine.infrastructure.ErrorMessageAdapter;

import coffee.machine.domain.DrinkInstructionFailed.DrinkInstructionFailed;
import coffee.machine.domain.KindOfDrink;

public class ErrorMessageIncompatibilityWithExtraHotAdapter extends ErrorMessageAdapter {

    private final DrinkInstructionFailed drinkInstructionFailed;

    public ErrorMessageIncompatibilityWithExtraHotAdapter(DrinkInstructionFailed drinkInstructionFailed) {
        this.drinkInstructionFailed = drinkInstructionFailed;
    }

    @Override
    public String formatMessage() {
        KindOfDrink kindOfDrink = drinkInstructionFailed.getDrink();
        return String.format("M:We can't deliver extra hot for %s", this.kindOfDrinksToDrinkLabel.get(kindOfDrink));
    }
}
