import java.util.HashMap;

public class FooBarQix {
    private CheckContent checkContent;
    private CheckDivisibility checkDivisibility;
    HashMap<Integer, String> symbols;

    public FooBarQix()
    {
        this(new HashMap<>() {{put(3, "Foo"); put(5, "Bar"); put(7, "Qix"); }});
    }

    public FooBarQix(HashMap<Integer, String> symbols) {
        this.symbols  = symbols;
        checkContent = new CheckContent(this.symbols);
        checkDivisibility = new CheckDivisibility(this.symbols);
    }

    public String generate(int number) {
        StringBuilder figure = checkDivisibility.isDivisibleBySymbols(number);
        figure.append(checkContent.containSymbols(number));

        return figure.toString().equals("") ?
                Integer.toString(number) :
                figure.toString();
    }
}


