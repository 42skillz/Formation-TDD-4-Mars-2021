package coffee.machine.domain;

public interface IValidateDrinkInstruction {
    DrinkInstruction checkValidityForDrinkPreparation(DrinkInstruction drinkInstruction);
}
