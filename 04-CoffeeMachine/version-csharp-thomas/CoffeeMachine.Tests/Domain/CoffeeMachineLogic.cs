namespace CoffeeMachine.Tests.Domain
{
    public class CoffeeMachineLogic
    {
        private readonly IMakeDrink _drinkMakerAdapter;
        private readonly ICheckBeverageQuantity _beverageQuantityChecker;
        private readonly INotifyViaEMail _emailNotifier;
        private readonly IForwardMessagesToEndUser _endUserForwarder;
        private readonly CoffeeMachineAnalytics _analytics;
        
        private decimal? _receivedMoney;
        private CustomerIncomingOrder _receivedOrder;

        public CoffeeMachineLogic(ITalkTheDrinkMakerProtocol drinkMakerProtocol, ICheckBeverageQuantity beverageQuantityChecker, INotifyViaEMail emailNotifier)
        {
            _drinkMakerAdapter = new DrinkMakerAdapter(drinkMakerProtocol);
            _endUserForwarder = new MessageToEndUserForwarder(drinkMakerProtocol);
            
            _beverageQuantityChecker = beverageQuantityChecker;
            _emailNotifier = emailNotifier;
            _analytics = new CoffeeMachineAnalytics();
        }
        
        private decimal ReceivedMoney => _receivedMoney ?? 0;

        public void Receive(CustomerIncomingOrder order)
        {
            if (ShortageIsDetectedForThisBeverage(order))
            {
                _endUserForwarder.SendMessage($"{order.Product.ToString()} shortage (a notification has been sent to our logistic division). Please pick another option.");
                _emailNotifier.NotifyMissingDrink(order.Product.ToString());
                return;
            }

            ReplacePreviousOrderWithNewOne(order);

            if (!ReceivedEnoughMoneyFor(order))
            {
                ComputeMissingAmountAndSendMessageForIt(order);
                return;
            }

            ExecuteDrinkTransaction(order);
        }

        public void ReceiveMoney(decimal amountInEuro)
        {
            CollectMoney(amountInEuro);

            if (ReceivedEnoughMoneyFor(_receivedOrder))
            {
                ExecuteDrinkTransaction(_receivedOrder);
            }
            else
            {
                ComputeMissingAmountAndSendMessageForIt(_receivedOrder);
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

        private void ReplacePreviousOrderWithNewOne(CustomerIncomingOrder order)
        {
            _receivedOrder = order;
        }

        private void ExecuteDrinkTransaction(CustomerIncomingOrder order)
        {
            _drinkMakerAdapter.MakeDrink(order);
            
            // Log the transaction
            _analytics.Record(_receivedOrder, ReceivedMoney);

            // close the transaction
            _receivedOrder = null;
            _receivedMoney = null;
        }

        private void ComputeMissingAmountAndSendMessageForIt(CustomerIncomingOrder order)
        {
            if (order == null)
            {
                _endUserForwarder.SendMessage($"Pick a beverage");
                return;
            }

            var missingAmount = Prices.ComputeMissingAmount(order.Product, ReceivedMoney);

            _endUserForwarder.SendMissingAmountMessage(missingAmount);
        }

        private void CollectMoney(decimal amountInEuro)
        {
            _receivedMoney = _receivedMoney + amountInEuro ?? amountInEuro;
        }

        private bool ReceivedEnoughMoneyFor(CustomerIncomingOrder order)
        {
            if (order == null)
            {
                return false;
            }

            return (ReceivedMoney >= Prices.GetUnitPriceFor(order.Product));
        }
    }
}