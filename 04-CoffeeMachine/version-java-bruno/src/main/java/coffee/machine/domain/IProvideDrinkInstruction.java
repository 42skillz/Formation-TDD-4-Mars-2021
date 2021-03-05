package coffee.machine.domain;

import coffee.machine.infrastructure.CustomerOrder;

public interface IProvideDrinkInstruction {
    DrinkInstruction adapt(CustomerOrder customerOrder);
}
