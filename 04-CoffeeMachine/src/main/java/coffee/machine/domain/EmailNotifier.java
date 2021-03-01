package coffee.machine.domain;

public interface EmailNotifier {
    void notifyMissingDrink(String drink);
}
