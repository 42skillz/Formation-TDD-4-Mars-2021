package coffee.machine.infrastructure;

import coffee.machine.domain.DrinkInstruction;
import coffee.machine.domain.DrinkInstructionFailed;
import coffee.machine.domain.IProvideDrinkInstruction;
import coffee.machine.domain.KindOfDrink;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DrinkInstructionAdapter implements IProvideDrinkInstruction {
    final HashMap<String, KindOfDrink> drinkLabelToKindOfDrinks = new HashMap<>();
    final List<KindOfDrink> extraHotDrinksAvailable = new ArrayList<>();

    public DrinkInstructionAdapter() {
        this.drinkLabelToKindOfDrinks.put("Tea", KindOfDrink.Tea);
        this.drinkLabelToKindOfDrinks.put("Chocolate", KindOfDrink.Chocolate);
        this.drinkLabelToKindOfDrinks.put("Coffee", KindOfDrink.Coffee);
        this.drinkLabelToKindOfDrinks.put("OrangeJuice", KindOfDrink.OrangeJuice);

        this.extraHotDrinksAvailable.addAll(
                new ArrayList<>() {{
                    add(KindOfDrink.Tea);
                    add(KindOfDrink.Coffee);
                    add(KindOfDrink.Chocolate);
                }});
    }

    public DrinkInstruction adapt(CustomerOrder order) {
        DrinkInstruction drinkInstruction = new DrinkInstruction();
        String drink = order.getDrink();

        if (drinkLabelToKindOfDrinks.containsKey(drink)) {
            drinkInstruction.setDrink(drinkLabelToKindOfDrinks.get(drink));
        } else {
            return forwardDrinkNotSupportedToCoffeeMachineUserInterface(drink);
        }

        drinkInstruction.setNbSugars(order.getNbSugars());
        drinkInstruction.setCustomerMoney(order.getMoney());

        if (order.getExtraHot() && thisDrinkDoNotSupportExtraHot(drinkInstruction)) {
            return forwardIncompatibilityWithExtraHotToUserInterface(drinkInstruction);
        }

        drinkInstruction.setExtraHot(order.getExtraHot());

        return drinkInstruction;
    }

    private DrinkInstructionFailed forwardDrinkNotSupportedToCoffeeMachineUserInterface(String drink) {
        return new DrinkInstructionFailed(String.format("M:%s: Sorry, this drink isn't supported yet", drink));
    }

    private DrinkInstructionFailed forwardIncompatibilityWithExtraHotToUserInterface(DrinkInstruction drinkInstruction) {
        return new DrinkInstructionFailed(String.format("M:We can't deliver extra hot for %s", drinkInstruction.getDrink()));
    }

    private boolean thisDrinkDoNotSupportExtraHot(DrinkInstruction drinkInstruction) {
        return !this.extraHotDrinksAvailable.contains(drinkInstruction.getDrink());
    }
}