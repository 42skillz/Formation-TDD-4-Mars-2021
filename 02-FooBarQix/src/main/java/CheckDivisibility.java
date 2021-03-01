import java.util.HashMap;

public class CheckDivisibility {

    private final HashMap<Integer, String> symbols;

    public CheckDivisibility(HashMap<Integer, String> symbols) {
        this.symbols = symbols;
    }

    public StringBuilder isDivisibleBySymbols(int number) {
        StringBuilder figure = new StringBuilder();
        for (Integer key : this.symbols.keySet()) {
            if (number % key == 0)
                figure.append(this.symbols.get(key));
        }
        return figure;
    }
}
