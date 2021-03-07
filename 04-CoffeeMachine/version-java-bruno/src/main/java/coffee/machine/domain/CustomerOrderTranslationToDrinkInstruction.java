package coffee.machine.domain;

import coffee.machine.domain.DrinkInstructionFailed.DrinkInstructionFailedBecauseBeverageIncompatibilityWithExtraHotFeature;
import coffee.machine.domain.DrinkInstructionFailed.DrinkInstructionFailedBecauseDrinkNotSupported;
import coffee.machine.infrastructure.CustomerOrder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomerOrderTranslationToDrinkInstruction implements ITranslateCustomerOrderToDrinkInstruction {

    static List<KindOfDrink> extraHotDrinksAvailable = Stream.of(
            KindOfDrink.TEA,
            KindOfDrink.COFFEE,
            KindOfDrink.CHOCOLATE)
            .collect(Collectors.toList());

    static Map<String, KindOfDrink> drinkLabelToKindOfDrinks = Map.of(
            "Tea", KindOfDrink.TEA,
            "Chocolate", KindOfDrink.CHOCOLATE,
            "Coffee", KindOfDrink.COFFEE,
            "OrangeJuice", KindOfDrink.ORANGE_JUICE
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
        return drinkLabelToKindOfDrinks.get(drink);
    }

    private boolean thisDrinkDoNotSupportExtraHot(DrinkInstruction drinkInstruction) {
        return !this.extraHotDrinksAvailable.contains(drinkInstruction.getDrink());
    }
}
