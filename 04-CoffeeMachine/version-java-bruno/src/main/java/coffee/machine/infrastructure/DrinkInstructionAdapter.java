package coffee.machine.infrastructure;

import coffee.machine.domain.DrinkInstruction;
import coffee.machine.domain.DrinkInstructionFailed.DrinkInstructionFailed;
import coffee.machine.domain.DrinkInstructionFailed.DrinkInstructionFailedDrinkNotSupported;
import coffee.machine.domain.DrinkInstructionFailed.DrinkInstructionFailedIncompatibilityWithExtraHot;
import coffee.machine.domain.IProvideDrinkInstruction;
import coffee.machine.domain.KindOfDrink;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DrinkInstructionAdapter implements IProvideDrinkInstruction {
    final HashMap<String, KindOfDrink> drinkLabelToKindOfDrinks = new HashMap<>();
    final List<KindOfDrink> extraHotDrinksAvailable = new ArrayList<>();

    public DrinkInstructionAdapter() {
        this.drinkLabelToKindOfDrinks.put("Tea", KindOfDrink.TEA);
        this.drinkLabelToKindOfDrinks.put("Chocolate", KindOfDrink.CHOCOLATE);
        this.drinkLabelToKindOfDrinks.put("Coffee", KindOfDrink.COFFEE);
        this.drinkLabelToKindOfDrinks.put("OrangeJuice", KindOfDrink.ORANGE_JUICE);

        this.extraHotDrinksAvailable.addAll(
                new ArrayList<>() {{
                    add(KindOfDrink.TEA);
                    add(KindOfDrink.COFFEE);
                    add(KindOfDrink.CHOCOLATE);
                }});
    }

    public DrinkInstruction adapt(CustomerOrder order) {
        DrinkInstruction drinkInstruction = new DrinkInstruction();

        if (drinkLabelToKindOfDrinks.containsKey(order.getDrink())) {
            drinkInstruction.setDrink(adaptDrink(order.getDrink()));
        } else {
            return forwardDrinkNotSupportedToCoffeeMachineUserInterface();
        }

        drinkInstruction.setNbSugars(order.getNbSugars());
        drinkInstruction.setCustomerMoney(order.getMoney());

        if (order.getExtraHot() && thisDrinkDoNotSupportExtraHot(drinkInstruction)) {
            return forwardIncompatibilityWithExtraHotToUserInterface(drinkInstruction);
        }

        drinkInstruction.setExtraHot(order.getExtraHot());

        return drinkInstruction;
    }

    public KindOfDrink adaptDrink(String drink) {
        return drinkLabelToKindOfDrinks.get(drink);
    }

    private DrinkInstructionFailed forwardDrinkNotSupportedToCoffeeMachineUserInterface() {
        return new DrinkInstructionFailedDrinkNotSupported();
    }

    private DrinkInstructionFailed forwardIncompatibilityWithExtraHotToUserInterface(DrinkInstruction drinkInstruction) {
        return new DrinkInstructionFailedIncompatibilityWithExtraHot(drinkInstruction);
    }

    private boolean thisDrinkDoNotSupportExtraHot(DrinkInstruction drinkInstruction) {
        return !this.extraHotDrinksAvailable.contains(drinkInstruction.getDrink());
    }
}