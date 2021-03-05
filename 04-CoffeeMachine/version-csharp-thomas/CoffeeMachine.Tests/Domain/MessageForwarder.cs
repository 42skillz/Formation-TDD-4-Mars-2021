namespace CoffeeMachine.Tests.Domain
{
    public class MessageForwarder
    {
        private readonly ITalkToTheDrinkMaker _drinkMakerAdapter;

        public MessageForwarder(ITalkToTheDrinkMaker drinkMakerAdapter)
        {
            _drinkMakerAdapter = drinkMakerAdapter;
        }

        public void SendMessage(string message)
        {
            _drinkMakerAdapter.Send($"M:{message}");
        }
    }
}