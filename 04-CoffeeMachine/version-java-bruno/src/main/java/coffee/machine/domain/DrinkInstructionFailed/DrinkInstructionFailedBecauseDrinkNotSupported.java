package coffee.machine.domain.DrinkInstructionFailed;

public class DrinkInstructionFailedBecauseDrinkNotSupported extends DrinkInstructionFailed {
    private final String drinkNotSupported;

    public DrinkInstructionFailedBecauseDrinkNotSupported(String drinkNotSupported) {
        this.kindOfDrinkInstructionFailed = KindOfDrinkInstructionFailed.DRINK_NOT_SUPPORTED;
        this.drinkNotSupported = drinkNotSupported;
    }

    public String getDrinkNotSupported() {
        return drinkNotSupported;
    }

}
