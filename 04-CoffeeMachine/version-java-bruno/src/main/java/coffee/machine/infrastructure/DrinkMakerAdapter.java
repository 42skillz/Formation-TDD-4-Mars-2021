package coffee.machine.infrastructure;

import coffee.machine.domain.DrinkInstruction;
import coffee.machine.domain.DrinkInstructionFailed.DrinkInstructionFailed;
import coffee.machine.domain.DrinkInstructionFailed.KindOfDrinkInstructionFailed;
import coffee.machine.domain.IAdaptCommandForTheDrinkMaker;
import coffee.machine.domain.KindOfDrink;
import coffee.machine.infrastructure.FormatMessageDrinkInstructionFailed.*;

import java.util.Map;

public class DrinkMakerAdapter implements IAdaptCommandForTheDrinkMaker {

    static Map<KindOfDrinkInstructionFailed, FormatMessageDrinkInstructionFailed> drinkInstructionByKinkOfDrinkInstructionFailed = Map.of(
            KindOfDrinkInstructionFailed.DrinkNotSupported, new FormatMessageForDrinkNotSupported(),
            KindOfDrinkInstructionFailed.DrinkShortage, new FormatMessageFailedShortageBeverage(),
            KindOfDrinkInstructionFailed.DrinkIncompatibilityWithExtraHot, new FormatMessageForIncompatibilityWithExtraHotFeature(),
            KindOfDrinkInstructionFailed.MissingMoney, new FormatMessageForMissingMoneyUser());

    static Map<KindOfDrink, Character> kindOfDrinkByDrinkMakerCommands = Map.of(
            KindOfDrink.TEA, 'T',
            KindOfDrink.CHOCOLATE, 'H',
            KindOfDrink.COFFEE, 'C',
            KindOfDrink.ORANGE_JUICE, 'O'
    );

    @Override
    public String FormatCommandToTheDrinkMaker(DrinkInstruction drinkInstruction) {
        return adaptDrink(drinkInstruction) + adaptExtraHot(drinkInstruction) + ':' +
                adaptNbSugars(drinkInstruction) + ':' + adaptStick(drinkInstruction);
    }

    @Override
    public String FormatMessageTowardTheUserInterface(DrinkInstructionFailed drinkInstructionFailed) {
        return formatMessageForDrinkInstructionFailed(drinkInstructionFailed);
    }

    private String formatMessageForDrinkInstructionFailed(DrinkInstructionFailed drinkInstruction) {
        return drinkInstructionByKinkOfDrinkInstructionFailed.get(drinkInstruction.getKindOfDrinkInstructionFailed())
                .formatMessage(drinkInstruction);
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
        return kindOfDrinkByDrinkMakerCommands.get(drinkInstruction.getDrink()).toString();
    }
}