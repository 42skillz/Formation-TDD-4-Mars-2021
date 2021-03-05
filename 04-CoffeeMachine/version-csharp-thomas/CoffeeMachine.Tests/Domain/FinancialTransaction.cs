namespace CoffeeMachine.Tests.Domain
{
    public class FinancialTransaction
    {
        public CustomerIncomingOrder Order { get; }

        public decimal? ReceivedMoney { get; }

        public FinancialTransaction(CustomerIncomingOrder order, decimal? receivedMoney)
        {
            Order = order;
            ReceivedMoney = receivedMoney;
        }
    }
}