package coffee.machine.domain.DrinkInstructionFailed;

import coffee.machine.domain.KindOfDrink;

public class DrinkInstructionFailedBecauseShortageBeverage extends DrinkInstructionFailed {
    private final KindOfDrink kindOfDrink;

    public DrinkInstructionFailedBecauseShortageBeverage(KindOfDrink kindOfDrink) {
        this.kindOfDrinkInstructionFailed = KindOfDrinkInstructionFailed.DRINK_SHORTAGE_BEVERAGE;
        this.kindOfDrink = kindOfDrink;
    }

    public KindOfDrink getDrink() {
        return this.kindOfDrink;
    }
}
