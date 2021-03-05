package coffee.machine.domain.DrinkInstructionFailed;

public class DrinkInstructionFailedDrinkNotSupported extends DrinkInstructionFailed {
    public DrinkInstructionFailedDrinkNotSupported() {
        this.kindDrinkInstructionFailed = KindDrinkInstructionFailed.DrinkNotSupported;
    }
}
