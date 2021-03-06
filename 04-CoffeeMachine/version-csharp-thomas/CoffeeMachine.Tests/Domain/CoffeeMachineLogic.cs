namespace CoffeeMachine.Tests.Domain
{
    public class CoffeeMachineLogic
    {
        private readonly IMakeDrink _drinkMakerAdapter;
        private readonly ICheckBeverageQuantity _beverageQuantityChecker;
        private readonly INotifyViaEMail _emailNotifier;
        private readonly IForwardMessagesToEndUser _endUserForwarder;
        private readonly CoffeeMachineAnalytics _analytics;
        
        private readonly Collector _collector;

        public CoffeeMachineLogic(ITalkTheDrinkMakerProtocol drinkMakerProtocol, ICheckBeverageQuantity beverageQuantityChecker, INotifyViaEMail emailNotifier)
        {
            _drinkMakerAdapter = new DrinkMakerAdapter(drinkMakerProtocol);
            _endUserForwarder = new MessageToEndUserForwarder(drinkMakerProtocol);
            
            _beverageQuantityChecker = beverageQuantityChecker;
            _emailNotifier = emailNotifier;
            _collector = new Collector();
            _analytics = new CoffeeMachineAnalytics(_collector);
        }
        
        public void Receive(CustomerIncomingOrder order)
        {
            if (ShortageIsDetectedForThisBeverage(order))
            {
                _endUserForwarder.SendMessage($"{order.Product.ToString()} shortage (a notification has been sent to our logistic division). Please pick another option.");
                _emailNotifier.NotifyMissingDrink(order.Product.ToString());
                return;
            }

            _collector.ReplacePreviousOrderWithNewOne(order);

            if (!_collector.ReceivedEnoughMoneyFor(order))
            {
                ComputeMissingAmountAndSendMessageForIt(order, _collector.ReceivedMoney);
                return;
            }

            ExecuteDrinkTransaction(order);
        }

        public void ReceiveMoney(decimal amountInEuro)
        {
            _collector.CollectMoney(amountInEuro);

            if (_collector.ReceivedEnoughMoneyFor(_collector.ReceivedOrder))
            {
                ExecuteDrinkTransaction(_collector.ReceivedOrder);
            }
            else
            {
                ComputeMissingAmountAndSendMessageForIt(_collector.ReceivedOrder, _collector.ReceivedMoney);
            }
        }

        public FinancialReport GetFinancialReport()
        {
            return _analytics.GenerateReport();
        }

        private bool ShortageIsDetectedForThisBeverage(CustomerIncomingOrder order)
        {
            return _beverageQuantityChecker.IsEmpty(order.Product.ToString());
        }

        private void ExecuteDrinkTransaction(CustomerIncomingOrder order)
        {
            _drinkMakerAdapter.MakeDrink(order);
            
            // Log the transaction
            _analytics.Record(_collector.ReceivedOrder, _collector.ReceivedMoney);

            // close the transaction
            _collector.CollectMoney();
            
        }

        public void ComputeMissingAmountAndSendMessageForIt(CustomerIncomingOrder order, decimal receivedMoney)
        {
            if (order == null)
            {
                _endUserForwarder.SendMessage($"Pick a beverage");
                return;
            }

            var missingAmount = Prices.ComputeMissingAmount(order.Product, receivedMoney);

            _endUserForwarder.SendMissingAmountMessage(missingAmount);
        }
    }
}