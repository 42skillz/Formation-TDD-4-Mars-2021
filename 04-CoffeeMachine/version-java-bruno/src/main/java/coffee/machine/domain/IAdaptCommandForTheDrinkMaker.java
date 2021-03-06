package coffee.machine.domain;

import coffee.machine.domain.DrinkInstructionFailed.DrinkInstructionFailed;

public interface IAdaptCommandForTheDrinkMaker {
    String FormatCommandToTheDrinkMaker(DrinkInstruction drinkInstruction);

    String FormatMessageTowardTheUserInterface(DrinkInstructionFailed drinkInstructionFailed);
}
