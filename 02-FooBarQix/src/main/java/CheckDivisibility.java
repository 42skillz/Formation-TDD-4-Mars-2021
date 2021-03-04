import java.util.HashMap;

public class CheckDivisibility {
    private final HashMap<Integer, String> symbols;

    public CheckDivisibility(HashMap<Integer, String> symbols) {
        this.symbols = symbols;
    }

    public StringBuilder applyDivisibilityRule(int number) {
        StringBuilder figures = new StringBuilder();
        for (Integer key : this.symbols.keySet()) {
            if (number % key == 0)
                figures.append(this.symbols.get(key));
        }
        return figures;
    }
}
