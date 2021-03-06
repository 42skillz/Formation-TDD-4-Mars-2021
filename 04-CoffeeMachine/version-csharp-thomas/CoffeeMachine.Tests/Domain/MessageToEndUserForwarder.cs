using System.Globalization;

namespace CoffeeMachine.Tests.Domain
{
    public class MessageToEndUserForwarder : IForwardMessagesToEndUser
    {
        private readonly ITalkTheDrinkMakerProtocol _drinkMakerProtocol;

        public MessageToEndUserForwarder(ITalkTheDrinkMakerProtocol drinkMakerProtocol)
        {
            _drinkMakerProtocol = drinkMakerProtocol;
        }

        public void SendMessage(string message)
        {
            _drinkMakerProtocol.Send($"M:{message}");
        }

        public void SendMissingAmountMessage(in decimal missingAmount)
        {
            _drinkMakerProtocol.Send($"M:{GenerateMissingAmountMessageWithProperCulture(missingAmount)}");
        }

        private static string GenerateMissingAmountMessageWithProperCulture(decimal missingAmount)
        {
            return $"Missing {missingAmount.ToString(new CultureInfo("en-US"))} euro";
        }
    }
}