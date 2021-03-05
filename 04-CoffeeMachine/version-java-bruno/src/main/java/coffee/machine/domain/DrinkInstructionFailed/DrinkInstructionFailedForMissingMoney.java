package coffee.machine.domain.DrinkInstructionFailed;

public class DrinkInstructionFailedForMissingMoney extends DrinkInstructionFailed {
    private final double moneyMissing;

    public DrinkInstructionFailedForMissingMoney(double moneyMissing) {
        kindDrinkInstructionFailed = KindDrinkInstructionFailed.MissingMoney;
        this.moneyMissing = moneyMissing;
    }

    public double getMoneyMissing() {
        return moneyMissing;
    }
}
