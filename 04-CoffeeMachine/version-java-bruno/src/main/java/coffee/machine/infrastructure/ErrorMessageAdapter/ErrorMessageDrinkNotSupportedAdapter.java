package coffee.machine.infrastructure.ErrorMessageAdapter;

import coffee.machine.domain.DrinkInstructionFailed.DrinkInstructionFailed;

public class ErrorMessageDrinkNotSupportedAdapter extends ErrorMessageAdapter {

    private final DrinkInstructionFailed drinkInstructionFailed;

    public ErrorMessageDrinkNotSupportedAdapter(DrinkInstructionFailed drinkInstructionFailed) {
        this.drinkInstructionFailed = drinkInstructionFailed;
    }

    @Override
    public String formatMessage() {
        return String.format("M:Drink %s not supported", this.kindOfDrinksToDrinkLabel.get(this.drinkInstructionFailed.getDrink()));
    }
}



