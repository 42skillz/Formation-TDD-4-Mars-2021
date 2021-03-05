using System;

namespace CoffeeMachine.Tests.Domain
{
    public static class Prices
    {
        public static decimal PriceForTea => 0.4m;

        public static decimal PriceForChocolate => 0.5m;

        public static decimal PriceForCoffee => 0.6m;

        public static decimal PriceForOrangeJuice => 0.6m;

        public static decimal GetUnitPriceFor(Product product)
        {
            switch (product)
            {
                case Product.Tea:
                    return PriceForTea;

                case Product.Coffee:
                    return PriceForCoffee;

                case Product.Chocolate:
                    return PriceForChocolate;

                case Product.OrangeJuice:
                    return PriceForOrangeJuice;

                default:
                    throw new ArgumentOutOfRangeException(nameof(product), product, null);
            }
        }
    }
}