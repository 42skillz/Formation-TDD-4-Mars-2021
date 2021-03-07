package coffee.machine.domain.DrinkInstructionFailed;

import coffee.machine.domain.DrinkInstruction;

public class DrinkInstructionFailedBecauseBeverageIncompatibilityWithExtraHotFeature extends DrinkInstructionFailed {
    public DrinkInstructionFailedBecauseBeverageIncompatibilityWithExtraHotFeature(DrinkInstruction drinkInstruction) {
        this.kindOfDrinkInstructionFailed = KindOfDrinkInstructionFailed.INCOMPATIBILITY_WITH_EXTRA_HOT_FEATURE;
        this.setDrink(drinkInstruction.getDrink());
    }
}
