import java.util.HashMap;

public class CheckContented {

    private final HashMap<Integer, String> symbols;
    public CheckContented(HashMap<Integer, String> symbols) {
        this.symbols = symbols;
    }

    StringBuilder containSymbols(int number) {
        StringBuilder figure = new StringBuilder();
        String stringNumber = Integer.toString(number);
        for (Character digit : stringNumber.toCharArray()) {
            for (Integer key : this.symbols.keySet()) {
                if (Integer.parseInt(String.valueOf(digit)) == key)
                    figure.append(this.symbols.get(key));
            }
        }
        return figure;
    }
}