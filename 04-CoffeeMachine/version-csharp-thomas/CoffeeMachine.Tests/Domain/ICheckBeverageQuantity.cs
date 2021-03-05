namespace CoffeeMachine.Tests.Domain
{
    public interface ICheckBeverageQuantity
    {
        bool IsEmpty(string drink);
    }
}