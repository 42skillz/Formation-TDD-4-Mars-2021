using System;
using System.Globalization;
using System.Text;

namespace CoffeeMachine.Tests.Domain
{
    public class CoffeeMachineLogic
    {
        private readonly ITalkToTheDrinkMaker _drinkMakerAdapter;
        private readonly ICheckBeverageQuantity _beverageQuantityChecker;
        private readonly INotifyViaEMail _emailNotifier;
        private decimal? _receivedMoney;
        private CustomerIncomingOrder _receivedOrder;
        private readonly MessageForwarder _messageForwarder;
        private readonly CoffeeMachineAnalytics _analytics = new CoffeeMachineAnalytics();

        public CoffeeMachineLogic(ITalkToTheDrinkMaker drinkMakerAdapter, ICheckBeverageQuantity beverageQuantityChecker, INotifyViaEMail emailNotifier)
        {
            _drinkMakerAdapter = drinkMakerAdapter;
            _beverageQuantityChecker = beverageQuantityChecker;
            _emailNotifier = emailNotifier;
            _messageForwarder = new MessageForwarder(drinkMakerAdapter);
        }

        public void Receive(CustomerIncomingOrder order)
        {
            if (ShortageIsDetectedForThisBeverage(order))
            {
                _messageForwarder.SendMessage($"{order.Product.ToString()} shortage (a notification has been sent to our logistic division). Please pick another option.");

                _emailNotifier.NotifyMissingDrink(order.Product.ToString());
                
                return;
            }

            ReplacePreviousOrderWithNewOne(order);

            if (!ReceivedEnoughMoney(order))
            {
                ComputeAndSendMissingAmount(order);
                return;
            }

            var instructions = AdaptToDrinkMakerInstruction(order);
            MakeBeverageTransaction(instructions);
        }

        private bool ShortageIsDetectedForThisBeverage(CustomerIncomingOrder order)
        {
            return _beverageQuantityChecker.IsEmpty(order.Product.ToString());
        }

        private void ReplacePreviousOrderWithNewOne(CustomerIncomingOrder order)
        {
            _receivedOrder = order;
        }

        private void MakeBeverageTransaction(string instructions)
        {
            _drinkMakerAdapter.Send(instructions);
            
            // Log the transaction
            _analytics.Record(_receivedOrder, _receivedMoney ?? 0);

            // close the transaction
            _receivedOrder = null;
            _receivedMoney = null;
        }

        private void ComputeAndSendMissingAmount(CustomerIncomingOrder order)
        {
            if (order == null)
            {
                _messageForwarder.SendMessage($"Pick a beverage");
                return;
            }

            var receivedMoney = _receivedMoney ?? 0;

            var missingAmount = ComputeMissingAmount(order, receivedMoney);

            _messageForwarder.SendMessage(GenerateMissingAmountMessageWithProperCulture(missingAmount));
        }

        private static string GenerateMissingAmountMessageWithProperCulture(decimal missingAmount)
        {
            return $"Missing {missingAmount.ToString(new CultureInfo("en-US"))} euro";
        }

        private decimal ComputeMissingAmount(CustomerIncomingOrder order, in decimal receivedMoney)
        {
            switch (order.Product)
            {
                case Product.Tea:
                    return Prices.PriceForTea - receivedMoney;

                case Product.Coffee:
                    return Prices.PriceForCoffee - receivedMoney;
                    
                case Product.Chocolate:
                    return Prices.PriceForChocolate - receivedMoney;

                case Product.OrangeJuice:
                    return Prices.PriceForOrangeJuice - receivedMoney;

                default:
                    throw new NotImplementedException();
            }
        }

        private static string AdaptToDrinkMakerInstruction(CustomerIncomingOrder order)
        {
            var instructions = new StringBuilder();

            instructions.Append(AdaptProduct(order));

            instructions.Append(":");

            instructions.Append(AdaptSugar(order));

            instructions.Append(":");

            instructions.Append(AdaptStick(order));

            return instructions.ToString();
        }

        public void ReceiveMoney(decimal amountInEuro)
        {
            CollectMoney(amountInEuro);

            if (ReceivedEnoughMoney(_receivedOrder))
            {
                var instruction = AdaptToDrinkMakerInstruction(_receivedOrder);
                
                MakeBeverageTransaction(instruction);
            }
            else
            {
                ComputeAndSendMissingAmount(_receivedOrder);
            }
        }

        private void CollectMoney(decimal amountInEuro)
        {
            _receivedMoney = _receivedMoney + amountInEuro ?? amountInEuro;
        }

        private bool ReceivedEnoughMoney(CustomerIncomingOrder order)
        {
            if (order == null)
            {
                return false;
            }

            switch (order.Product)
            {
                case Product.Tea:
                    
                    return (_receivedMoney.HasValue && _receivedMoney.Value >= Prices.PriceForTea);
                    
                case Product.Coffee:
                    return (_receivedMoney.HasValue && _receivedMoney.Value >= Prices.PriceForCoffee);

                case Product.Chocolate:
                    return (_receivedMoney.HasValue && _receivedMoney.Value >= Prices.PriceForChocolate);

                case Product.OrangeJuice:
                    return (_receivedMoney.HasValue && _receivedMoney.Value >= Prices.PriceForOrangeJuice);

                default:
                    return false;
            }
        }

        private static string AdaptStick(CustomerIncomingOrder order)
        {
            if (order.NbOfSugar > 0 || (order.WithStick.HasValue && order.WithStick.Value == true))
            {
                return "0";
            }

            return string.Empty;
        }

        private static string AdaptSugar(CustomerIncomingOrder order)
        {
            if (order.NbOfSugar != 0)
            {
                return $"{order.NbOfSugar}";
            }

            return string.Empty;
        }

        private static string AdaptProduct(CustomerIncomingOrder order)
        {
            return order.Product switch
            {
                Product.Tea => HasOrderedExtraHotAndItIsRelevant(order) ? "Th" : "T",
                Product.Chocolate => HasOrderedExtraHotAndItIsRelevant(order) ? "Hh" : "H",
                Product.Coffee => HasOrderedExtraHotAndItIsRelevant(order) ? "Ch":"C",
                Product.OrangeJuice => HasOrderedExtraHotAndItIsRelevant(order) ? "Oh" : "O", // makes no sense but prepare for upcoming refactoring with dictionary
                _ => string.Empty
            };
        }

        private static bool HasOrderedExtraHotAndItIsRelevant(CustomerIncomingOrder order)
        {
            if (order.Product == Product.OrangeJuice)
            {
                return false;
            }

            return order.ExtraHot.HasValue && order.ExtraHot.Value == true;
        }

        public FinancialReport GetFinancialReport()
        {
            return _analytics.GenerateReport();
        }
    }
}