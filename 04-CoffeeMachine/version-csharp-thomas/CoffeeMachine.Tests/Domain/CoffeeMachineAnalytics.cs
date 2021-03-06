using System.Collections.Generic;
using System.Linq;

namespace CoffeeMachine.Tests.Domain
{
    public class CoffeeMachineAnalytics
    {
        private readonly Collector _collector;
        private decimal _totalAmountInEuro = 0;

        private readonly List<FinancialTransaction> _transactions = new List<FinancialTransaction>();

        public CoffeeMachineAnalytics(Collector collector)
        {
            _collector = collector;
        }

        public FinancialReport GenerateReport()
        {
            var nbOfChocolateSold = _transactions.Count(x => x.Order.Product == Product.Chocolate);
            var nbOfCoffeeSold = _transactions.Count(x => x.Order.Product == Product.Coffee);
            var nbOfTeaSold = _transactions.Count(x => x.Order.Product == Product.Tea);
            var nbOfOrangeJuiceSold = _transactions.Count(x => x.Order.Product == Product.OrangeJuice);

            return new FinancialReport(_collector.TotalCollectedAmountInEuro, nbOfChocolateSold, nbOfCoffeeSold, nbOfTeaSold, nbOfOrangeJuiceSold);
        }

        public void Record(CustomerIncomingOrder customerIncomingOrder, decimal receivedMoney)
        {
            //_totalAmountInEuro += receivedMoney;

            _transactions.Add(new FinancialTransaction(customerIncomingOrder, receivedMoney));
        }
    }
}