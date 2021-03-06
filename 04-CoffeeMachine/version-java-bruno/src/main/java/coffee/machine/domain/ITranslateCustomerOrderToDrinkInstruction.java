package coffee.machine.domain;

import coffee.machine.infrastructure.CustomerOrder;

public interface ITranslateCustomerOrderToDrinkInstruction {
    DrinkInstruction Translate(CustomerOrder customerOrder);
}
