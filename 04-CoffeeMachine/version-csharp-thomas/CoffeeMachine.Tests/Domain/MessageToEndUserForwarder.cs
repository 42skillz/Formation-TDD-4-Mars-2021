using System.Globalization;

namespace CoffeeMachine.Tests.Domain
{
    public class MessageToEndUserForwarder : IForwardMessagesToEndUser
    {
        private readonly ITalkToTheDrinkMaker _drinkMakerAdapter;

        public MessageToEndUserForwarder(ITalkToTheDrinkMaker drinkMakerAdapter)
        {
            _drinkMakerAdapter = drinkMakerAdapter;
        }

        public void SendMessage(string message)
        {
            _drinkMakerAdapter.Send($"M:{message}");
        }

        public void SendMissingAmountMessage(in decimal missingAmount)
        {
            _drinkMakerAdapter.Send($"M:{GenerateMissingAmountMessageWithProperCulture(missingAmount)}");
        }

        private static string GenerateMissingAmountMessageWithProperCulture(decimal missingAmount)
        {
            return $"Missing {missingAmount.ToString(new CultureInfo("en-US"))} euro";
        }
    }
}