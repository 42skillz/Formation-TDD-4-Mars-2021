package coffee.machine.domain;

import coffee.machine.domain.DrinkInstructionFailed.DrinkInstructionFailedBecauseBeverageIncompatibilityWithExtraHotFeature;
import coffee.machine.domain.DrinkInstructionFailed.DrinkInstructionFailedBecauseDrinkNotSupported;
import coffee.machine.infrastructure.CustomerOrder;
import org.javatuples.Pair;

import java.util.Map;

public class CustomerOrderTranslationToDrinkInstruction implements ITranslateCustomerOrderToDrinkInstruction {

    static Map<String, Pair<KindOfDrink, Boolean>> drinkLabelToKindOfDrinks = Map.of(
            "Tea", new Pair<>(KindOfDrink.TEA, true),
            "Chocolate", new Pair<>(KindOfDrink.CHOCOLATE, true),
            "Coffee", new Pair<>(KindOfDrink.COFFEE, true),
            "OrangeJuice", new Pair<>(KindOfDrink.ORANGE_JUICE, false)
    );

    @Override
    public DrinkInstruction Translate(CustomerOrder customerOrder) {
        DrinkInstruction drinkInstruction = new DrinkInstruction();

        if (drinkLabelToKindOfDrinks.containsKey(customerOrder.getDrink())) {
            drinkInstruction.setDrink(adaptDrink(customerOrder.getDrink()));
        } else {
            return new DrinkInstructionFailedBecauseDrinkNotSupported(customerOrder.getDrink());
        }

        drinkInstruction.setNbSugars(customerOrder.getNbSugars());
        drinkInstruction.setCustomerMoney(customerOrder.getMoney());

        if (customerOrder.getExtraHot() && this.thisDrinkDoNotSupportExtraHot(drinkInstruction)) {
            return new DrinkInstructionFailedBecauseBeverageIncompatibilityWithExtraHotFeature(drinkInstruction);
        }

        drinkInstruction.setExtraHot(customerOrder.getExtraHot());

        return drinkInstruction;
    }

    public KindOfDrink adaptDrink(String drink) {
        return drinkLabelToKindOfDrinks.get(drink).getValue0();
    }

    private boolean thisDrinkDoNotSupportExtraHot(DrinkInstruction drinkInstruction) {
        for (Pair<KindOfDrink, Boolean> item: drinkLabelToKindOfDrinks.values()) {
            if (item.getValue0().equals(drinkInstruction.getDrink())) {
                return !item.getValue1();
            }
        }
        return true;
    }
}
