namespace CoffeeMachine.Tests.Domain
{
    public interface IForwardMessages
    {
        void SendMessage(string message);
        void SendMissingAmountMessage(in decimal missingAmount);
    }
}