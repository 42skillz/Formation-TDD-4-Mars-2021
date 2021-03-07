package coffee.machine.domain;

import coffee.machine.domain.DrinkInstructionFailed.DrinkInstructionFailedBecauseBeverageIncompatibilityWithExtraHotFeature;
import coffee.machine.domain.DrinkInstructionFailed.DrinkInstructionFailedBecauseDrinkNotSupported;
import coffee.machine.infrastructure.CustomerOrder;
import org.javatuples.Pair;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class CustomerOrderTranslationToDrinkInstruction implements ITranslateCustomerOrderToDrinkInstruction {

    static Map<String, Pair<KindOfDrink, Boolean>> drinkLabelToKindOfDrinksExtraHot = Map.of(
            "Tea", new Pair<>(KindOfDrink.TEA, true),
            "Chocolate", new Pair<>(KindOfDrink.CHOCOLATE, true),
            "Coffee", new Pair<>(KindOfDrink.COFFEE, true),
            "OrangeJuice", new Pair<>(KindOfDrink.ORANGE_JUICE, false)
    );

    @Override
    public DrinkInstruction Translate(CustomerOrder customerOrder) {
        DrinkInstruction drinkInstruction = new DrinkInstruction();

        if (drinkLabelToKindOfDrinksExtraHot.containsKey(customerOrder.getDrink())) {
            drinkInstruction.setDrink(adaptDrink(customerOrder.getDrink()));
        }
        else {
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
        return drinkLabelToKindOfDrinksExtraHot.get(drink).getValue0();
    }

    private boolean thisDrinkDoNotSupportExtraHot(DrinkInstruction drinkInstruction) {
        return !drinkLabelToKindOfDrinksExtraHot.values().stream()
                .filter((item) -> item.getValue0().equals(drinkInstruction.getDrink()))
                    .map(p -> p.getValue1()).findAny()
                        .orElse(false);
    }
}
