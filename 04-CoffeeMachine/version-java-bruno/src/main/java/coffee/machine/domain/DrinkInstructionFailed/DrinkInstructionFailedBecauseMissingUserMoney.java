package coffee.machine.domain.DrinkInstructionFailed;

public class DrinkInstructionFailedBecauseMissingUserMoney extends DrinkInstructionFailed {
    private final double moneyMissing;

    public DrinkInstructionFailedBecauseMissingUserMoney(double moneyMissing) {
        kindOfDrinkInstructionFailed = KindOfDrinkInstructionFailed.MissingMoney;
        this.moneyMissing = moneyMissing;
    }

    public double getMoneyMissing() {
        return moneyMissing;
    }
}
