package coffee.machine;

import coffee.machine.domain.BeverageQuantityChecker;
import coffee.machine.domain.CoffeeMachineLogic;
import coffee.machine.domain.EmailNotifier;
import coffee.machine.domain.FinancialReport;
import coffee.machine.infrastructure.DrinkInstructionAdapter;
import coffee.machine.infrastructure.DrinkMakerAdapter;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

public class CoffeeMachineLogicBuilder {
    private FinancialReport financialReport;
    private BeverageQuantityChecker beverageQuantityChecker;
    private EmailNotifier emailNotifier;

    public CoffeeMachineLogicBuilder() {
        financialReport = null;
        beverageQuantityChecker = null;
        emailNotifier = null;
    }

    public DrinkMakerAdapter buildCoffeeMachineLogic() {
        var coffeeMachineLogic = new CoffeeMachineLogic(beverageQuantityChecker, emailNotifier, financialReport);
        return new DrinkMakerAdapter(coffeeMachineLogic, new DrinkInstructionAdapter());
    }

    public CoffeeMachineLogicBuilder withFinancialReport(FinancialReport financialReport) {
        this.financialReport = financialReport;
        return this;
    }

    public CoffeeMachineLogicBuilder withBeverageQuantityChecker(String drink, boolean isEmpty) {

        this.beverageQuantityChecker = Mockito.mock(BeverageQuantityChecker.class);
        when(beverageQuantityChecker.isEmpty(drink)).thenReturn(isEmpty);
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

