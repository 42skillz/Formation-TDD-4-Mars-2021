package coffee.machine.domain;

import coffee.machine.domain.DrinkInstructionFailed.DrinkInstructionFailed;
import coffee.machine.domain.DrinkInstructionFailed.DrinkInstructionFailedForMissingMoney;
import coffee.machine.domain.DrinkInstructionFailed.DrinkInstructionFailedShortage;

import java.util.HashMap;

public class CoffeeMachineLogic implements IValidateDrinkInstruction {
    final HashMap<KindOfDrink, Double> drinkPrices = new HashMap<>();
    private final BeverageQuantityChecker beverageQuantityChecker;
    private final EmailNotifier emailNotifier;
    private final FinancialReport financialReport;

    public CoffeeMachineLogic(BeverageQuantityChecker beverageQuantityChecker, EmailNotifier emailNotifier, FinancialReport businessReport) {
        this.beverageQuantityChecker = beverageQuantityChecker;
        this.emailNotifier = emailNotifier;
        this.financialReport = businessReport;

        this.drinkPrices.put(KindOfDrink.TEA, 0.4);
        this.drinkPrices.put(KindOfDrink.COFFEE, 0.6);
        this.drinkPrices.put(KindOfDrink.CHOCOLATE, 0.5);
        this.drinkPrices.put(KindOfDrink.ORANGE_JUICE, 0.6);
    }

    public DrinkInstruction checkValidityForDrinkPreparation(DrinkInstruction drinkInstruction) {

        if (this.isThereShortageForThisDrink(drinkInstruction)) {
            return this.drinkInstructionFailedForShortage(drinkInstruction);
        }

        if (!drinkInstruction.isCustomerHaveEnoughMoney(this.drinkPrices)) {
            return this.drinkInstructionFailedForMissingMoney(drinkInstruction);
        }

        if (this.isFinancialReportIsNeeded()) {
            this.financialReport.addBeverageSold(drinkInstruction, this.drinkPrices);
        }

        return drinkInstruction;
    }

    private boolean isThereShortageForThisDrink(DrinkInstruction drinkInstruction) {
        return beverageQuantityChecker != null && beverageQuantityChecker.isEmpty(drinkInstruction.getDrink());
    }

    private DrinkInstructionFailed drinkInstructionFailedForShortage(DrinkInstruction drinkInstruction) {
        if (this.emailNotifier != null) {
            this.emailNotifier.notifyMissingDrink(drinkInstruction.getDrink().toString());
        }
        return new DrinkInstructionFailedShortage(drinkInstruction.getDrink());
    }

    private boolean isFinancialReportIsNeeded() {
        return this.financialReport != null;
    }

    private DrinkInstruction drinkInstructionFailedForMissingMoney(DrinkInstruction instruction) {
        double moneyMissing = drinkPrices.get(instruction.drink) - instruction.customerMoney;
        return new DrinkInstructionFailedForMissingMoney(moneyMissing);
    }
}
