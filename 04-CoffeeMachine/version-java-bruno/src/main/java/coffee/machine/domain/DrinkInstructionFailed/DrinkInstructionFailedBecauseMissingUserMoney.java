package coffee.machine.domain.DrinkInstructionFailed;

public class DrinkInstructionFailedBecauseMissingUserMoney extends DrinkInstructionFailed {
    private final double moneyMissing;

    public DrinkInstructionFailedBecauseMissingUserMoney(double moneyMissing) {
        this.kindOfDrinkInstructionFailed = KindOfDrinkInstructionFailed.MISSING_USER_MONEY;
        this.moneyMissing = moneyMissing;
    }

    public double getMoneyMissing() {
        return moneyMissing;
    }
}
