package coffee.machine.infrastructure;

import coffee.machine.domain.DrinkInstruction;
import coffee.machine.domain.DrinkInstructionFailed.DrinkInstructionFailed;
import coffee.machine.domain.IProvideDrinkInstruction;
import coffee.machine.domain.IValidateDrinkInstruction;
import coffee.machine.domain.KindOfDrink;
import coffee.machine.infrastructure.ErrorMessageAdapter.*;

import java.util.HashMap;

public class DrinkMakerAdapter {

    final HashMap<KindOfDrink, Character> kindOfDrinkToDrinkDigit = new HashMap<>();
    private final IValidateDrinkInstruction coffeeMachineLogic;
    private final IProvideDrinkInstruction provideDrinkInstructions;

    public DrinkMakerAdapter(IValidateDrinkInstruction logicService, IProvideDrinkInstruction provideDrinkInstructions) {
        this.coffeeMachineLogic = logicService;
        this.provideDrinkInstructions = provideDrinkInstructions;
        this.kindOfDrinkToDrinkDigit.put(KindOfDrink.TEA, 'T');
        this.kindOfDrinkToDrinkDigit.put(KindOfDrink.CHOCOLATE, 'H');
        this.kindOfDrinkToDrinkDigit.put(KindOfDrink.COFFEE, 'C');
        this.kindOfDrinkToDrinkDigit.put(KindOfDrink.ORANGE_JUICE, 'O');
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
            return SendErrorMessageToCoffeeMachine((DrinkInstructionFailed) drinkInstruction);
        }
        return adaptDrinkInstruction(drinkInstruction);
    }

    private String SendErrorMessageToCoffeeMachine(DrinkInstructionFailed drinkInstructionFailed) {
        HashMap<DrinkInstructionFailed.KindDrinkInstructionFailed, ErrorMessageAdapter> adaptErrorMessages = new HashMap<>();

        adaptErrorMessages.put(DrinkInstructionFailed.KindDrinkInstructionFailed.DrinkNotSupported, new ErrorMessageDrinkNotSupportedAdapter(drinkInstructionFailed));
        adaptErrorMessages.put(DrinkInstructionFailed.KindDrinkInstructionFailed.DrinkShortage, new ErrorMessageFailedShortageAdapter(drinkInstructionFailed));
        adaptErrorMessages.put(DrinkInstructionFailed.KindDrinkInstructionFailed.DrinkIncompatibilityWithExtraHot, new ErrorMessageIncompatibilityWithExtraHotAdapter(drinkInstructionFailed));
        adaptErrorMessages.put(DrinkInstructionFailed.KindDrinkInstructionFailed.MissingMoney, new ErrorMessageMissingMoneyAdapter(drinkInstructionFailed));

        return adaptErrorMessages.get(drinkInstructionFailed.getKindDrinkInstructionFailed()).formatMessage();

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

    private String adaptDrink(DrinkInstruction drinkInstruction) {
        return this.kindOfDrinkToDrinkDigit.get(drinkInstruction.getDrink()).toString();
    }
}