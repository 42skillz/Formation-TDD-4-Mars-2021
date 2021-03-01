package coffee.machine.domain;

import java.util.HashMap;

public class DrinkInstruction {
    KindOfDrink drink;
    int nbSugars;
    double customerMoney;
    private boolean extraHot;

    public void setCustomerMoney(double customerMoney) {
        this.customerMoney = customerMoney;
    }

    public KindOfDrink getDrink() {
        return drink;
    }

    public void setDrink(KindOfDrink drink) {
        this.drink = drink;
    }

    public int getNbSugars() {
        return nbSugars;
    }

    public void setNbSugars(int nbSugars) {
        this.nbSugars = nbSugars;
    }

    boolean isCustomerHaveEnoughMoney(HashMap<KindOfDrink, Double> drinkPrices) {
        return customerMoney >= drinkPrices.get(getDrink());
    }

    public boolean getExtraHot() {
        return this.extraHot;
    }

    public void setExtraHot(boolean extraHot) {
        this.extraHot = extraHot;
    }
}
