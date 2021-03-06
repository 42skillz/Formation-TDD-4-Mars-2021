using CoffeeMachine.Tests.Domain;
using NSubstitute;

namespace CoffeeMachine.Tests
{
    public class CoffeeMachineBuilder
    {
        private ITalkTheDrinkMakerProtocol _drinkMakerProtocol;
        private ICheckBeverageQuantity _beverageQuantityChecker;
        private INotifyViaEMail _emailNotifier;

        public CoffeeMachineBuilder WithDrinkMakerProtocol(ITalkTheDrinkMakerProtocol drinkMakerProtocol)
        {
            _drinkMakerProtocol = drinkMakerProtocol;
            return this;
        }

        public CoffeeMachineBuilder WithBeverageQuantityChecker(ICheckBeverageQuantity beverageQuantityChecker)
        {
            _beverageQuantityChecker = beverageQuantityChecker;

            return this;
        }

        public CoffeeMachineBuilder WithEMailNotifier(INotifyViaEMail emailNotifier)
        {
            _emailNotifier = emailNotifier;

            return this;
        }

        public CoffeeMachineLogic BuildCoffeeMachineLogic()
        {
            var beverageQuantityChecker = _beverageQuantityChecker ?? Substitute.For<ICheckBeverageQuantity>();
            var notifyViaEMail = _emailNotifier ?? Substitute.For<INotifyViaEMail>();

            var machineLogic = new CoffeeMachineLogic(_drinkMakerProtocol, beverageQuantityChecker, notifyViaEMail);

            return machineLogic;
        }
    }
}