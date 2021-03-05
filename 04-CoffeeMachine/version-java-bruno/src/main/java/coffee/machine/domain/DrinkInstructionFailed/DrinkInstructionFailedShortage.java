package coffee.machine.domain.DrinkInstructionFailed;

import coffee.machine.domain.KindOfDrink;

public class DrinkInstructionFailedShortage extends DrinkInstructionFailed {

    private final KindOfDrink kindOfDrink;

    public DrinkInstructionFailedShortage(KindOfDrink kindOfDrink) {
        kindDrinkInstructionFailed = KindDrinkInstructionFailed.DrinkShortage;
        this.kindOfDrink = kindOfDrink;
    }

    public KindOfDrink getDrink() {
        return this.kindOfDrink;
    }
}
