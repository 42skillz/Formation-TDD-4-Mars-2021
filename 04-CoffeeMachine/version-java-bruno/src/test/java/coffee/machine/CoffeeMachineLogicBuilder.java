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
        orderTranslation = new CustomerOrderTranslationToDrinkInstruction();
        financialReport = null;
        beverageQuantityChecker = null;
        emailNotifier = null;
    }

    public CoffeeMachineLogic build() {
        return new CoffeeMachineLogic(new DrinkMakerAdapter(), orderTranslation, beverageQuantityChecker, emailNotifier, financialReport);
    }

    public CoffeeMachineLogicBuilder withFinancialReport(FinancialReport financialReport) {
        this.financialReport = financialReport;
        return this;
    }

    public CoffeeMachineLogicBuilder withBeverageQuantityChecker(String drink, boolean isEmpty) {
        this.beverageQuantityChecker = Mockito.mock(BeverageQuantityChecker.class);
        when(beverageQuantityChecker.isEmpty(this.orderTranslation.adaptDrink(drink))).thenReturn(isEmpty);
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

