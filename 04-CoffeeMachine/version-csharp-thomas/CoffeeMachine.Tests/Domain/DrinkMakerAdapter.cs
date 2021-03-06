using System.Text;

namespace CoffeeMachine.Tests.Domain
{
    public class DrinkMakerAdapter
    {
        public static string AdaptToDrinkMakerInstruction(CustomerIncomingOrder order)
        {
            var instructions = new StringBuilder();

            instructions.Append(AdaptProduct(order));

            instructions.Append(":");

            instructions.Append(AdaptSugar(order));

            instructions.Append(":");

            instructions.Append(AdaptStick(order));

            return instructions.ToString();
        }

        private static string AdaptProduct(CustomerIncomingOrder order)
        {
            return order.Product switch
            {
                Product.Tea => HasOrderedExtraHotAndItIsRelevant(order) ? "Th" : "T",
                Product.Chocolate => HasOrderedExtraHotAndItIsRelevant(order) ? "Hh" : "H",
                Product.Coffee => HasOrderedExtraHotAndItIsRelevant(order) ? "Ch" : "C",
                Product.OrangeJuice => HasOrderedExtraHotAndItIsRelevant(order) ? "Oh" : "O", // makes no sense but prepare for upcoming refactoring with dictionary
                _ => string.Empty
            };
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

        private static bool HasOrderedExtraHotAndItIsRelevant(CustomerIncomingOrder order)
        {
            if (order.Product == Product.OrangeJuice)
            {
                return false;
            }

            return order.ExtraHot.HasValue && order.ExtraHot.Value == true;
        }
    }
}