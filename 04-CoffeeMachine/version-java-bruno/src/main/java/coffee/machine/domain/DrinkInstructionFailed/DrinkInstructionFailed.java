package coffee.machine.domain.DrinkInstructionFailed;

import coffee.machine.domain.DrinkInstruction;

public class DrinkInstructionFailed extends DrinkInstruction {
    KindDrinkInstructionFailed kindDrinkInstructionFailed;

    public DrinkInstructionFailed() {
        kindDrinkInstructionFailed = KindDrinkInstructionFailed.None;
    }

    public KindDrinkInstructionFailed getKindDrinkInstructionFailed() {
        return this.kindDrinkInstructionFailed;
    }

    public enum KindDrinkInstructionFailed {
        None,
        MissingMoney,
        DrinkShortage,
        DrinkNotSupported,
        DrinkIncompatibilityWithExtraHot,
    }
}
