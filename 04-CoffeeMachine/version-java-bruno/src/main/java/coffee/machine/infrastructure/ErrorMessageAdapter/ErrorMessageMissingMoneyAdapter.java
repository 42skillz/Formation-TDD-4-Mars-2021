package coffee.machine.infrastructure.ErrorMessageAdapter;

import coffee.machine.domain.DrinkInstructionFailed.DrinkInstructionFailed;
import coffee.machine.domain.DrinkInstructionFailed.DrinkInstructionFailedForMissingMoney;

public class ErrorMessageMissingMoneyAdapter extends ErrorMessageAdapter {
    private final DrinkInstructionFailed drinkInstructionFailed;

    public ErrorMessageMissingMoneyAdapter(DrinkInstructionFailed drinkInstructionFailed) {
        this.drinkInstructionFailed = drinkInstructionFailed;
    }

    @Override
    public String formatMessage() {
        double moneyMissing = ((DrinkInstructionFailedForMissingMoney) this.drinkInstructionFailed).getMoneyMissing();
        return String.format("M:Missing %.2f euro", moneyMissing);
    }
}