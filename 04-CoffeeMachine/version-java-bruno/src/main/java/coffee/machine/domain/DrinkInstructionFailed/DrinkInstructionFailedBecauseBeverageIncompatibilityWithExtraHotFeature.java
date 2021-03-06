package coffee.machine.domain.DrinkInstructionFailed;

import coffee.machine.domain.DrinkInstruction;

public class DrinkInstructionFailedBecauseBeverageIncompatibilityWithExtraHotFeature extends DrinkInstructionFailed {
    public DrinkInstructionFailedBecauseBeverageIncompatibilityWithExtraHotFeature(DrinkInstruction drinkInstruction) {
        this.kindOfDrinkInstructionFailed = KindOfDrinkInstructionFailed.DrinkIncompatibilityWithExtraHot;
        this.setDrink(drinkInstruction.getDrink());
    }
}
