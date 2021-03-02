import java.util.HashMap;

public class CheckContent {

    private final HashMap<Integer, String> symbols;
    public CheckContent(HashMap<Integer, String> symbols) {
        this.symbols = symbols;
    }

    StringBuilder containSymbols(int number) {
        StringBuilder figures = new StringBuilder();
        String stringNumber = Integer.toString(number);
        for (Character digit : stringNumber.toCharArray()) {
            for (Integer key : this.symbols.keySet()) {
                if (Integer.parseInt(String.valueOf(digit)) == key)
                    figures.append(this.symbols.get(key));
            }
        }
        return figures;
    }
}