package coffee.machine.domain.DrinkInstructionFailed;

import coffee.machine.domain.DrinkInstruction;

public class DrinkInstructionFailedIncompatibilityWithExtraHot extends DrinkInstructionFailed {
    public DrinkInstructionFailedIncompatibilityWithExtraHot(DrinkInstruction drinkInstruction) {
        this.setDrink(drinkInstruction.getDrink());
        this.kindDrinkInstructionFailed = KindDrinkInstructionFailed.DrinkIncompatibilityWithExtraHot;
    }
}
