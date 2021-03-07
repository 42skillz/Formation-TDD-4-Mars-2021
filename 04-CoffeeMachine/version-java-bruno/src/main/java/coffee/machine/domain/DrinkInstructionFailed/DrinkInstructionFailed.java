package coffee.machine.domain.DrinkInstructionFailed;

import coffee.machine.domain.DrinkInstruction;

public class DrinkInstructionFailed extends DrinkInstruction {
    protected KindOfDrinkInstructionFailed kindOfDrinkInstructionFailed = KindOfDrinkInstructionFailed.INSTRUCTION_FAILED;

    public KindOfDrinkInstructionFailed getKindOfDrinkInstructionFailed() {
        return this.kindOfDrinkInstructionFailed;
    }
}
