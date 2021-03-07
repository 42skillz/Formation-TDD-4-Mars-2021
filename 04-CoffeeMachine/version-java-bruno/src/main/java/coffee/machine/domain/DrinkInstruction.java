package coffee.machine.domain;

import java.util.Map;

public class DrinkInstruction {
    private KindOfDrink drink;
    private int nbSugars;
    private double customerMoney;
    private boolean extraHot;

    public KindOfDrink getDrink() {
        return this.drink;
    }

    public void setDrink(KindOfDrink drink) {
        this.drink = drink;
    }

    public int getNbSugars() {
        return this.nbSugars;
    }

    public void setNbSugars(int nbSugars) {
        this.nbSugars = nbSugars;
    }

    boolean isCustomerHaveEnoughMoney(Map<KindOfDrink, Double> drinkPrices) {
        return this.customerMoney >= drinkPrices.get(getDrink());
    }

    public boolean getExtraHot() {
        return this.extraHot;
    }

    public void setExtraHot(boolean extraHot) {
        this.extraHot = extraHot;
    }

    double getCustomerMoney() { return this.customerMoney; }

    void setCustomerMoney(double customerMoney) {
        this.customerMoney = customerMoney;
    }
}
