import java.util.HashMap;

public class FooBarQix {
    private CheckContented checkContented;
    private CheckDivisibility checkDivisibility;
    HashMap<Integer, String> symbols;

    public FooBarQix()
    {
        this(new HashMap<>() {{put(3, "Foo"); put(5, "Bar"); put(7, "Qix"); }});
    }

    public FooBarQix(HashMap<Integer, String> symbols) {
        this.symbols  = symbols;
        checkContented = new CheckContented(this.symbols);
        checkDivisibility = new CheckDivisibility(this.symbols);
    }

    public String generate(int number) {
        StringBuilder figure = checkDivisibility.isDivisibleBySymbols(number);

        figure.append(checkContented.containSymbols(number));

        return figure.toString().equals("") ?
                Integer.toString(number) :
                figure.toString();
    }
}


