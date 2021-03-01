package coffee.machine.domain;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;

public class FinancialReport {
    private final HashMap<KindOfDrink, Integer> kindOfDrinks;
    private double totalAmount;

    public FinancialReport() {
        kindOfDrinks = new HashMap<>();
    }

    public int totalBeverageSold() {
        return kindOfDrinks.values().stream().reduce(Integer::sum).orElse(0);
    }

    public int beverageCount(KindOfDrink kindOfDrink) {
        return kindOfDrinks.get(kindOfDrink);
    }

    public double totalAmount() {
        BigDecimal amount = new BigDecimal(this.totalAmount);
        MathContext mathContext = new MathContext(2);
        return amount.round(mathContext).doubleValue();
    }

    public void addBeverageSold(DrinkInstruction instructions, HashMap<KindOfDrink, Double> drinkPrices) {
        if (!this.kindOfDrinks.containsKey(instructions.getDrink())) {
            this.kindOfDrinks.put(instructions.getDrink(), 1);
        } else {
            int drinkCount = this.kindOfDrinks.get(instructions.getDrink());
            this.kindOfDrinks.put(instructions.getDrink(), ++drinkCount);
        }
        totalAmount += drinkPrices.get(instructions.getDrink());
    }
}
