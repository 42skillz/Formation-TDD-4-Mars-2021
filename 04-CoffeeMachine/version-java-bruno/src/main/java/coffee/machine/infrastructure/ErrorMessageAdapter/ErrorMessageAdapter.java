package coffee.machine.infrastructure.ErrorMessageAdapter;

import coffee.machine.domain.KindOfDrink;

import java.util.HashMap;

public class ErrorMessageAdapter {
    HashMap<KindOfDrink, String> kindOfDrinksToDrinkLabel = new HashMap<>();

    public ErrorMessageAdapter() {
        this.kindOfDrinksToDrinkLabel.put(KindOfDrink.TEA, "Tea");
        this.kindOfDrinksToDrinkLabel.put(KindOfDrink.CHOCOLATE, "Chocolate");
        this.kindOfDrinksToDrinkLabel.put(KindOfDrink.COFFEE, "Coffee");
        this.kindOfDrinksToDrinkLabel.put(KindOfDrink.ORANGE_JUICE, "OrangeJuice");
    }

    public String formatMessage() {
        return null;
    }
}
