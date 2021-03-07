package coffee.machine;

import coffee.machine.domain.*;
import coffee.machine.infrastructure.DrinkMakerAdapter;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

public class CoffeeMachineLogicBuilder {
    private final CustomerOrderTranslationToDrinkInstruction orderTranslation;
    private FinancialReport financialReport;
    private BeverageQuantityChecker beverageQuantityChecker;
    private EmailNotifier emailNotifier;

    public CoffeeMachineLogicBuilder() {
        this.orderTranslation = new CustomerOrderTranslationToDrinkInstruction();
        this.financialReport = null;
        this.beverageQuantityChecker = null;
        this.emailNotifier = null;
    }

    public CoffeeMachineLogic build() {
        return new CoffeeMachineLogic(
                new DrinkMakerAdapter(),
                this.orderTranslation,
                this.beverageQuantityChecker,
                this.emailNotifier,
                this.financialReport);
    }

    public CoffeeMachineLogicBuilder withFinancialReport(FinancialReport financialReport) {
        this.financialReport = financialReport;
        return this;
    }

    public CoffeeMachineLogicBuilder withBeverageQuantityChecker(String drink, boolean isEmpty) {
        this.beverageQuantityChecker = Mockito.mock(BeverageQuantityChecker.class);
        when(this.beverageQuantityChecker.isEmpty(this.orderTranslation.adaptDrink(drink))).thenReturn(isEmpty);
        return this;
    }

    public CoffeeMachineLogicBuilder withEMailNotifier() {
        this.emailNotifier = Mockito.mock(EmailNotifier.class);
        return this;
    }

    public EmailNotifier getEMailNotifier() {
        return this.emailNotifier;
    }
}

