import java.util.HashMap;
import java.util.Map;

public class FooBarQix {
    private final CheckContent checkContent;
    private final CheckDivisibility checkDivisibility;

    public FooBarQix() {
        this(new HashMap<>() {{
            put(3, "Foo" );
            put(5, "Bar" );
            put(7, "Qix" );
        }});
    }

    public FooBarQix(HashMap<Integer, String> symbols) {

        checkContent = new CheckContent(symbols);
        checkDivisibility = new CheckDivisibility(symbols);
    }

    public String generate(int number) {
        StringBuilder figure = checkDivisibility.applyDivisibilityRule(number);
        figure.append(checkContent.applyContentRule(number));

        return figure.toString().equals("" ) ?
                Integer.toString(number) :
                figure.toString();
    }
}


