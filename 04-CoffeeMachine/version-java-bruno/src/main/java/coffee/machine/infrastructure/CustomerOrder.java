package coffee.machine.infrastructure;

public class CustomerOrder {
    private final String drink;
    private final boolean extraHot;
    private final int nbSugars;
    private final double customerMoney;

    public CustomerOrder(String drink, boolean extraHot, int nbSugars, double customerMoney) {
        this.drink = drink;
        this.extraHot = extraHot;
        this.nbSugars = nbSugars;
        this.customerMoney = customerMoney;
    }

    public String getDrink() {
        return drink;
    }

    public double getMoney() {
        return this.customerMoney;
    }

    public int getNbSugars() {
        return this.nbSugars;
    }

    public boolean getExtraHot() {
        return this.extraHot;
    }
}
