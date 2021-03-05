namespace CoffeeMachine.Tests.Domain
{
    public interface ITalkToTheDrinkMaker
    {
        void Send(string instructionsProtocol);
    }
}