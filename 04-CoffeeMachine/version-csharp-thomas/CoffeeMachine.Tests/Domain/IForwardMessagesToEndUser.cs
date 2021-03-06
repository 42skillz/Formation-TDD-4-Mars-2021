namespace CoffeeMachine.Tests.Domain
{
    public interface IForwardMessagesToEndUser
    {
        void SendMessage(string message);
        void SendMissingAmountMessage(in decimal missingAmount);
    }
}