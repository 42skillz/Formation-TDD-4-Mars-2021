package coffee.machine.infrastructure;

import coffee.machine.domain.*;

import java.util.HashMap;

public class DrinkMakerAdapter {

    final HashMap<KindOfDrink, Character> kindOfDrinkToDrinkDigit = new HashMap<>();
    private final IValidateDrinkInstruction coffeeMachineLogic;
    private final IProvideDrinkInstruction provideDrinkInstructions;

    public DrinkMakerAdapter(IValidateDrinkInstruction logicService, IProvideDrinkInstruction provideDrinkInstructions) {
        this.coffeeMachineLogic = logicService;
        this.provideDrinkInstructions = provideDrinkInstructions;
        this.kindOfDrinkToDrinkDigit.put(KindOfDrink.Tea, 'T');
        this.kindOfDrinkToDrinkDigit.put(KindOfDrink.Chocolate, 'H');
        this.kindOfDrinkToDrinkDigit.put(KindOfDrink.Coffee, 'C');
        this.kindOfDrinkToDrinkDigit.put(KindOfDrink.OrangeJuice, 'O');
    }

    public String makeCommand(CustomerOrder order) {
        // From infrastructure => to Domain
        DrinkInstruction drinkInstruction = provideDrinkInstructions.adapt(order);

        if (this.isDrinkInstructionFailed(drinkInstruction)) {
            return this.adapt(drinkInstruction);
        }

        drinkInstruction = this.coffeeMachineLogic.checkValidityForDrinkPreparation(drinkInstruction);

        // From Domain => infrastructure
        return adapt(drinkInstruction);
    }

    public String adapt(DrinkInstruction drinkInstruction) {
        if (isDrinkInstructionFailed(drinkInstruction)) {
            return SendErrorMessageToCoffeeMachine(drinkInstruction);
        }
        return adaptDrinkInstruction(drinkInstruction);
    }

    private String SendErrorMessageToCoffeeMachine(DrinkInstruction drinkInstruction) {
        return ((DrinkInstructionFailed) drinkInstruction).getErrorMessage();
    }

    private String adaptDrinkInstruction(DrinkInstruction drinkInstruction) {
        return adaptDrink(drinkInstruction) +
                adaptExtraHot(drinkInstruction) +
                ':' +
                adaptNbSugars(drinkInstruction) +
                ':' +
                adaptStick(drinkInstruction);
    }

    private boolean isDrinkInstructionFailed(DrinkInstruction drinkInstruction) {
        return drinkInstruction instanceof DrinkInstructionFailed;
    }

    private String adaptStick(DrinkInstruction instructions) {
        return instructions.getNbSugars() <= 0 ? "" : "0";
    }

    private String adaptNbSugars(DrinkInstruction instructions) {
        return instructions.getNbSugars() > 0 ? Integer.toString(instructions.getNbSugars()) : "";
    }

    private String adaptExtraHot(DrinkInstruction instructions) {
        return instructions.getExtraHot() ? "h" : "";
    }

    private String adaptDrink(DrinkInstruction instructions) {
        return this.kindOfDrinkToDrinkDigit.get(instructions.getDrink()).toString();
    }
}