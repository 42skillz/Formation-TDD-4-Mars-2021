namespace CoffeeMachine.Tests.Domain
{
    public interface IMakeDrink
    {
        void MakeDrink(CustomerIncomingOrder order);
    }
}