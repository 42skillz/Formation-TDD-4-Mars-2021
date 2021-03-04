import java.util.HashMap;

public class CheckContent {
    private final HashMap<Integer, String> symbols;

    public CheckContent(HashMap<Integer, String> symbols) {
        this.symbols = symbols;
    }

    StringBuilder applyContentRule(int number) {
        StringBuilder figures = new StringBuilder();
        for (Character digit : Integer.toString(number).toCharArray()) {
            for (Integer key : this.symbols.keySet()) {
                if (Integer.parseInt(String.valueOf(digit)) == key) {
                    figures.append(this.symbols.get(key));
                }
            }
        }
        return figures;
    }
}