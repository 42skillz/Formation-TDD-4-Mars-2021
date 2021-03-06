namespace CoffeeMachine.Tests.Domain
{
    public class Collector
    {
        private decimal? _receivedMoney;
        private CustomerIncomingOrder _receivedOrder;

        public void ReplacePreviousOrderWithNewOne(CustomerIncomingOrder order)
        {
            ReceivedOrder = order;
        }

        public decimal ReceivedMoney => _receivedMoney ?? 0;

        public CustomerIncomingOrder ReceivedOrder
        {
            set => _receivedOrder = value;
            get => _receivedOrder;
        }

        public decimal TotalCollectedAmountInEuro { get; set; } = 0;

        public void CollectMoney()
        {
            AddToCashRegister(_receivedMoney.Value);
            
            _receivedMoney = null;
            _receivedOrder = null;
        }

        private void AddToCashRegister(decimal money)
        {
            TotalCollectedAmountInEuro += money;
        }

        public void CollectMoney(decimal amountInEuro)
        {
            _receivedMoney = _receivedMoney + amountInEuro ?? amountInEuro;
        }

        public bool ReceivedEnoughMoneyFor(CustomerIncomingOrder order)
        {
            if (order == null)
            {
                return false;
            }

            return (ReceivedMoney >= Prices.GetUnitPriceFor(order.Product));
        }
    }
}