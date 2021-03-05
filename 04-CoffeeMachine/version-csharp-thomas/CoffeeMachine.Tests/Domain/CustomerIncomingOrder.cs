namespace CoffeeMachine.Tests.Domain
{
    public class CustomerIncomingOrder
    {
        public Product Product { get; }
        public int NbOfSugar { get; }
        public bool? WithStick { get; }
        public bool? ExtraHot { get; }

        public CustomerIncomingOrder(Product product, int? nbOfSugar = 0, bool? withStick = null, bool? extraHot = null)
        {
            Product = product;
            NbOfSugar = nbOfSugar??0;
            WithStick = withStick;
            ExtraHot = extraHot;
        }
    }
}