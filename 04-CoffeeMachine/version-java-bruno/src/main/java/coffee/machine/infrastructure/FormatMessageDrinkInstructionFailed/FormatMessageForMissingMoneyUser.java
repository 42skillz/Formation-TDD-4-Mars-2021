package coffee.machine.infrastructure.FormatMessageDrinkInstructionFailed;

import coffee.machine.domain.DrinkInstructionFailed.DrinkInstructionFailed;
import coffee.machine.domain.DrinkInstructionFailed.DrinkInstructionFailedBecauseMissingUserMoney;

public class FormatMessageForMissingMoneyUser extends FormatMessageDrinkInstructionFailed {
    @Override
    public String formatMessage(DrinkInstructionFailed drinkInstructionFailed) {
        double moneyMissing = ((DrinkInstructionFailedBecauseMissingUserMoney) drinkInstructionFailed).getMoneyMissing();
        return String.format("M:Missing %.2f euro", moneyMissing);
    }
}