using CoffeeMachine.Tests.Domain;
using NFluent;
using NSubstitute;
using NUnit.Framework;

namespace CoffeeMachine.Tests
{
    [TestFixture]
    public class CoffeeMachineLogicShould
    {
        [Test]
        public void Be_able_to_ask_a_Tea_with_1_Sugar_and_a_Stick_to_the_DrinkMaker_if_received_enough_money_for_it_before()
        {
            var drinkMakerProtocol = Substitute.For<ITalkTheDrinkMakerProtocol>();

            var machineLogic = new CoffeeMachineBuilder()
                .WithDrinkMakerProtocol(drinkMakerProtocol)
                .BuildCoffeeMachineLogic();

            machineLogic.ReceiveMoney(0.4m);
            drinkMakerProtocol.Received(1).Send("M:Pick a beverage");

            var order = new CustomerIncomingOrder(Product.Tea, nbOfSugar: 1, withStick: true);
            machineLogic.Receive(order);

            drinkMakerProtocol.Received(1).Send("T:1:0");
        }

        [Test]
        public void Be_able_to_ask_a_Tea_with_1_Sugar_and_a_Stick_to_the_DrinkMaker_if_received_enough_money_for_it_after()
        {
            var drinkMakerProtocol = Substitute.For<ITalkTheDrinkMakerProtocol>();

            var machineLogic = new CoffeeMachineBuilder()
                .WithDrinkMakerProtocol(drinkMakerProtocol)
                .BuildCoffeeMachineLogic();

            var order = new CustomerIncomingOrder(Product.Tea, nbOfSugar: 1, withStick: true);
            machineLogic.Receive(order);

            drinkMakerProtocol.Received(1).Send("M:Missing 0.4 euro");

            machineLogic.ReceiveMoney(0.4m);
            drinkMakerProtocol.Received(1).Send("T:1:0");
        }

        [Test]
        public void Be_able_to_ask_one_Chocolate_with_no_Sugar_and_no_Stick_to_the_DrinkMaker()
        {
            var drinkMakerProtocol = Substitute.For<ITalkTheDrinkMakerProtocol>();

            var machineLogic = new CoffeeMachineBuilder()
                .WithDrinkMakerProtocol(drinkMakerProtocol)
                .BuildCoffeeMachineLogic();

            var order = new CustomerIncomingOrder(Product.Chocolate, nbOfSugar: 0, withStick: false);
            machineLogic.Receive(order);

            drinkMakerProtocol.Received(1).Send("M:Missing 0.5 euro");

            machineLogic.ReceiveMoney(0.5m);
            drinkMakerProtocol.Received(1).Send("H::");
        }

        [Test]
        public void Be_able_to_ask_a_Coffee_with_2_Sugar_and_a_Stick_to_the_DrinkMaker()
        {
            var drinkMakerProtocol = Substitute.For<ITalkTheDrinkMakerProtocol>();

            var machineLogic = new CoffeeMachineBuilder()
                .WithDrinkMakerProtocol(drinkMakerProtocol)
                .BuildCoffeeMachineLogic();

            var order = new CustomerIncomingOrder(Product.Coffee, nbOfSugar: 2, withStick: true);
            machineLogic.Receive(order);

            drinkMakerProtocol.Received(1).Send("M:Missing 0.6 euro");

            machineLogic.ReceiveMoney(0.6m);
            drinkMakerProtocol.Received(1).Send("C:2:0");
        }

        [Test]
        public void Automatically_ask_a_Stick_as_soon_as_some_Sugar_is_ordered()
        {
            var drinkMakerProtocol = Substitute.For<ITalkTheDrinkMakerProtocol>();

            var machineLogic = new CoffeeMachineBuilder()
                .WithDrinkMakerProtocol(drinkMakerProtocol)
                .BuildCoffeeMachineLogic();

            machineLogic.ReceiveMoney(0.6m);
            drinkMakerProtocol.Received(1).Send("M:Pick a beverage");

            var order = new CustomerIncomingOrder(Product.Coffee, nbOfSugar: 3);
            machineLogic.Receive(order);

            drinkMakerProtocol.Received(1).Send("C:3:0");
        }

        [Test]
        public void Send_the_amount_of_missing_money_to_the_DrinkMaker_when_no_money_has_been_received()
        {
            var drinkMakerProtocol = Substitute.For<ITalkTheDrinkMakerProtocol>();

            var machineLogic = new CoffeeMachineBuilder()
                .WithDrinkMakerProtocol(drinkMakerProtocol)
                .BuildCoffeeMachineLogic();

            var order = new CustomerIncomingOrder(Product.Tea, nbOfSugar: 1, withStick: true);
            machineLogic.Receive(order);

            drinkMakerProtocol.Received(1).Send("M:Missing 0.4 euro");
        }

        [Test]
        public void Send_the_amount_of_missing_money_to_the_DrinkMaker_when_some_money_has_already_been_received_but_not_enough()
        {
            var drinkMakerProtocol = Substitute.For<ITalkTheDrinkMakerProtocol>();

            var machineLogic = new CoffeeMachineBuilder()
                .WithDrinkMakerProtocol(drinkMakerProtocol)
                .BuildCoffeeMachineLogic();

            machineLogic.ReceiveMoney(0.1m);
            drinkMakerProtocol.Received(1).Send("M:Pick a beverage");

            var order = new CustomerIncomingOrder(Product.Tea, nbOfSugar: 1, withStick: true);
            machineLogic.Receive(order);

            drinkMakerProtocol.Received(1).Send("M:Missing 0.3 euro");
        }


        [Test]
        public void Send_the_amount_of_missing_money_to_the_DrinkMaker_when_some_money_has_already_been_received_but_not_enough_and_ReceiveMoney_is_called_after()
        {
            var drinkMakerProtocol = Substitute.For<ITalkTheDrinkMakerProtocol>();

            var machineLogic = new CoffeeMachineBuilder()
                .WithDrinkMakerProtocol(drinkMakerProtocol)
                .BuildCoffeeMachineLogic();

            var order = new CustomerIncomingOrder(Product.Chocolate, nbOfSugar: 1, withStick: true);
            machineLogic.Receive(order);
            
            drinkMakerProtocol.Received(1).Send("M:Missing 0.5 euro");

            machineLogic.ReceiveMoney(0.1m);
            drinkMakerProtocol.Received(1).Send("M:Missing 0.4 euro");
        }

        [Test]
        public void Not_prepare_a_free_beverage_just_after_providing_one()
        {
            var drinkMakerProtocol = Substitute.For<ITalkTheDrinkMakerProtocol>();

            var machineLogic = new CoffeeMachineBuilder()
                .WithDrinkMakerProtocol(drinkMakerProtocol)
                .BuildCoffeeMachineLogic();

            var order = new CustomerIncomingOrder(Product.Chocolate, nbOfSugar: 1, withStick: true);
            machineLogic.Receive(order);

            drinkMakerProtocol.Received(1).Send("M:Missing 0.5 euro");

            machineLogic.ReceiveMoney(0.5m);
            drinkMakerProtocol.Received(1).Send("H:1:0");

            drinkMakerProtocol.ClearReceivedCalls();

            // Ask the same beverage
            machineLogic.Receive(order);
            drinkMakerProtocol.Received(1).Send("M:Missing 0.5 euro");
        }

        [Test]
        public void Be_able_to_ask_an_Orange_juice_for_60_cents_of_euro()
        {
            var drinkMakerProtocol = Substitute.For<ITalkTheDrinkMakerProtocol>();

            var machineLogic = new CoffeeMachineBuilder()
                .WithDrinkMakerProtocol(drinkMakerProtocol)
                .BuildCoffeeMachineLogic();

            machineLogic.ReceiveMoney(0.6m);
            drinkMakerProtocol.Received(1).Send("M:Pick a beverage");

            var order = new CustomerIncomingOrder(Product.OrangeJuice);
            machineLogic.Receive(order);
            
            drinkMakerProtocol.Received(1).Send("O::");
        }

        [TestCase(Product.Coffee, "Ch::")]
        [TestCase(Product.Chocolate, "Hh::")]
        [TestCase(Product.Tea, "Th::")]
        public void Be_able_to_ask_an_ExtraHot_HotBeverage_with_no_sugar(Product product, string expectedInstructions)
        {
            var drinkMakerProtocol = Substitute.For<ITalkTheDrinkMakerProtocol>();

            var machineLogic = new CoffeeMachineBuilder()
                .WithDrinkMakerProtocol(drinkMakerProtocol)
                .BuildCoffeeMachineLogic();


            machineLogic.ReceiveMoney(0.6m);
            drinkMakerProtocol.Received(1).Send("M:Pick a beverage");

            var order = new CustomerIncomingOrder(product, extraHot: true);
            machineLogic.Receive(order);
            
            drinkMakerProtocol.Received(1).Send(expectedInstructions);
        }

        [Test]
        public void Never_ask_an_ExtraHot_Orange_juice_to_the_DrinkMaker_whatever_the_initial_Order()
        {
            var drinkMakerProtocol = Substitute.For<ITalkTheDrinkMakerProtocol>();

            var machineLogic = new CoffeeMachineBuilder()
                .WithDrinkMakerProtocol(drinkMakerProtocol)
                .BuildCoffeeMachineLogic();

            machineLogic.ReceiveMoney(0.6m);
            drinkMakerProtocol.Received(1).Send("M:Pick a beverage");

            var order = new CustomerIncomingOrder(Product.OrangeJuice, extraHot: true);
            machineLogic.Receive(order);

            drinkMakerProtocol.Received(1).Send("O::");
        }

        [Test]
        public void Be_able_to_print_a_report_with_how_many_drinks_were_sold_and_the_total_amount_of_money_Earned_so_far()
        {
            var drinkMakerProtocol = Substitute.For<ITalkTheDrinkMakerProtocol>();

            var machineLogic = new CoffeeMachineBuilder()
                .WithDrinkMakerProtocol(drinkMakerProtocol)
                .BuildCoffeeMachineLogic();

            var report = machineLogic.GetFinancialReport();

            CheckThatReportHasTheseValues(report, totalAmountInEuro: 0, nbOfChocolateSold: 0, 
                nbOfCoffeeSold: 0, nbOfTeaSold: 0, nbOfOrangeJuiceSold: 0);

            BuyABeverage(machineLogic, Product.OrangeJuice);
            BuyABeverage(machineLogic, Product.Coffee);
            BuyABeverage(machineLogic, Product.Chocolate);

            report = machineLogic.GetFinancialReport();

            CheckThatReportHasTheseValues(report, totalAmountInEuro: 1.7m, nbOfChocolateSold: 1,
                nbOfCoffeeSold: 1, nbOfTeaSold: 0, nbOfOrangeJuiceSold: 1);
        }

        [Test]
        public void Notify_via_a_message_but_also_notify_us_via_EMail_when_shortage_is_detected()
        {
            var drinkMakerProtocol = Substitute.For<ITalkTheDrinkMakerProtocol>();
            var beverageQuantityChecker = Substitute.For<ICheckBeverageQuantity>();
            var emailNotifier = Substitute.For<INotifyViaEMail>();

            var machineLogic = new CoffeeMachineBuilder()
                .WithDrinkMakerProtocol(drinkMakerProtocol)
                .WithBeverageQuantityChecker(beverageQuantityChecker)
                .WithEMailNotifier(emailNotifier)
                .BuildCoffeeMachineLogic();

            SimulateAShortageOfOrangeJuice(beverageQuantityChecker);

            var order = new CustomerIncomingOrder(Product.OrangeJuice, extraHot: true);
            machineLogic.Receive(order);

            drinkMakerProtocol.Received().Send("M:OrangeJuice shortage (a notification has been sent to our logistic division). Please pick another option.");

            CheckThatAnEmailNotificationHasBeenSentForOrangeJuiceShortage(emailNotifier);
        }

        private static void CheckThatReportHasTheseValues(FinancialReport report, decimal totalAmountInEuro, int nbOfChocolateSold, int nbOfCoffeeSold, int nbOfTeaSold, int nbOfOrangeJuiceSold)
        {
            Check.That(report.TotalAmountInEuro).IsEqualTo(totalAmountInEuro);
            Check.That(report.NbOfChocolateSold).IsEqualTo(nbOfChocolateSold);
            Check.That(report.NbOfCoffeeSold).IsEqualTo(nbOfCoffeeSold);
            Check.That(report.NbOfTeaSold).IsEqualTo(nbOfTeaSold);
            Check.That(report.NbOfOrangeJuiceSold).IsEqualTo(nbOfOrangeJuiceSold);
        }

        private static void CheckThatAnEmailNotificationHasBeenSentForOrangeJuiceShortage(INotifyViaEMail notifyViaEMailMock)
        {
            notifyViaEMailMock.Received(1).NotifyMissingDrink(Product.OrangeJuice.ToString());
        }

        private static void SimulateAShortageOfOrangeJuice(ICheckBeverageQuantity beverageQuantityChecker)
        {
            beverageQuantityChecker.IsEmpty(Product.OrangeJuice.ToString()).Returns(true);
        }

        private static void BuyABeverage(CoffeeMachineLogic machineLogic, Product product)
        {
            var order = new CustomerIncomingOrder(product);
            machineLogic.Receive(order);

            var neededAmount = Prices.GetUnitPriceFor(product);

            machineLogic.ReceiveMoney(neededAmount);
        }
    }
}