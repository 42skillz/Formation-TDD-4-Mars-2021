package coffee.machine.domain;

import coffee.machine.domain.DrinkInstructionFailed.DrinkInstructionFailed;
import coffee.machine.domain.DrinkInstructionFailed.DrinkInstructionFailedBecauseMissingUserMoney;
import coffee.machine.domain.DrinkInstructionFailed.DrinkInstructionFailedBecauseShortageBeverage;
import coffee.machine.infrastructure.CustomerOrder;

import java.util.Map;

public class CoffeeMachineLogic {
    static Map<KindOfDrink, Double> kindOfDrinkPrices = Map.of(
            KindOfDrink.TEA, 0.4,
            KindOfDrink.COFFEE, 0.6,
            KindOfDrink.CHOCOLATE, 0.5,
            KindOfDrink.ORANGE_JUICE, 0.6
    );
    private final IAdaptCommandForTheDrinkMaker talkToTheDrinkMaker;
    private final ITranslateCustomerOrderToDrinkInstruction translateOrderToDrinkInstruction;
    private final BeverageQuantityChecker beverageQuantityChecker;
    private final EmailNotifier emailNotifier;
    private final FinancialReport financialReport;

    public CoffeeMachineLogic(IAdaptCommandForTheDrinkMaker talkToTheDrinkMaker, ITranslateCustomerOrderToDrinkInstruction translateOrderToDrinkInstruction, BeverageQuantityChecker beverageQuantityChecker, EmailNotifier emailNotifier, FinancialReport businessReport) {
        this.talkToTheDrinkMaker = talkToTheDrinkMaker;
        this.translateOrderToDrinkInstruction = translateOrderToDrinkInstruction;
        this.beverageQuantityChecker = beverageQuantityChecker;
        this.emailNotifier = emailNotifier;
        this.financialReport = businessReport;
    }

    public String makeCommand(CustomerOrder order) {
        DrinkInstruction drinkInstruction = this.translateOrderToDrinkInstruction.Translate(order);

        if (isDrinkInstructionSucceeded(drinkInstruction)) {
            drinkInstruction = checkTheIntegrityOfTheDrinkInstruction(drinkInstruction);
        }
        return isDrinkInstructionSucceeded(drinkInstruction) ?
                this.talkToTheDrinkMaker.FormatCommandToTheDrinkMaker(drinkInstruction) :
                this.talkToTheDrinkMaker.FormatMessageTowardTheUserInterface(ToDrinkInstructionFailed(drinkInstruction));
    }

    private boolean isDrinkInstructionSucceeded(DrinkInstruction drinkInstruction) {
        return !(drinkInstruction instanceof DrinkInstructionFailed);
    }

    public DrinkInstruction checkTheIntegrityOfTheDrinkInstruction(DrinkInstruction drinkInstruction) {

        if (this.isThereShortageForThisDrink(drinkInstruction)) {
            return this.drinkInstructionFailedForBeverageShortage(drinkInstruction);
        }

        if (!drinkInstruction.isCustomerHaveEnoughMoney(kindOfDrinkPrices)) {
            return this.drinkInstructionFailedBecauseMissingUserMoney(drinkInstruction);
        }

        if (this.isFinancialReportIsNeeded()) {
            this.financialReport.addBeverageSold(drinkInstruction, kindOfDrinkPrices);
        }

        return drinkInstruction;
    }

    private DrinkInstructionFailed ToDrinkInstructionFailed(DrinkInstruction drinkInstruction) {
        return (DrinkInstructionFailed) drinkInstruction;
    }

    private boolean isThereShortageForThisDrink(DrinkInstruction drinkInstruction) {
        return beverageQuantityChecker != null && beverageQuantityChecker.isEmpty(drinkInstruction.getDrink());
    }

    private DrinkInstructionFailed drinkInstructionFailedForBeverageShortage(DrinkInstruction drinkInstruction) {
        if (this.emailNotifier != null) {
            this.emailNotifier.notifyMissingDrink(drinkInstruction.getDrink().toString());
        }
        return new DrinkInstructionFailedBecauseShortageBeverage(drinkInstruction.getDrink());
    }

    private boolean isFinancialReportIsNeeded() {
        return this.financialReport != null;
    }

    private DrinkInstruction drinkInstructionFailedBecauseMissingUserMoney(DrinkInstruction instruction) {
        return new DrinkInstructionFailedBecauseMissingUserMoney(kindOfDrinkPrices.get(instruction.drink) - instruction.customerMoney);
    }
}