package coffee.machine.domain;

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

        this.drinkPrices.put(KindOfDrink.Tea, 0.4);
        this.drinkPrices.put(KindOfDrink.Coffee, 0.6);
        this.drinkPrices.put(KindOfDrink.Chocolate, 0.5);
        this.drinkPrices.put(KindOfDrink.OrangeJuice, 0.6);
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
        return beverageQuantityChecker != null && beverageQuantityChecker.isEmpty(drinkInstruction.getDrink().toString());
    }

    private DrinkInstructionFailed drinkInstructionFailedForShortage(DrinkInstruction instructions) {
        if (this.emailNotifier != null) {
            this.emailNotifier.notifyMissingDrink(instructions.getDrink().toString());
        }
        return new DrinkInstructionFailed(String.format("M:%s shortage (a notification has been sent to our logistic division). Please pick another option.", instructions.getDrink().toString()));
    }

    private boolean isFinancialReportIsNeeded() {
        return this.financialReport != null;
    }

    private DrinkInstruction drinkInstructionFailedForMissingMoney(DrinkInstruction instruction) {
        double moneyMissing = drinkPrices.get(instruction.drink) - instruction.customerMoney;
        return new DrinkInstructionFailed(String.format("M:Missing %.2f euro", moneyMissing));
    }
}
