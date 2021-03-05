namespace CoffeeMachine.Tests
{
    public class FinancialReport
    {
        public decimal TotalAmountInEuro { get; }

        public int NbOfChocolateSold { get; }
        public int NbOfCoffeeSold { get; }
        public int NbOfTeaSold { get; }
        public int NbOfOrangeJuiceSold { get; }

        public FinancialReport(decimal totalAmountInEuro, int nbOfChocolateSold, int nbOfCoffeeSold, int nbOfTeaSold, int nbOfOrangeJuiceSold)
        {
            TotalAmountInEuro = totalAmountInEuro;
            NbOfChocolateSold = nbOfChocolateSold;
            NbOfCoffeeSold = nbOfCoffeeSold;
            NbOfTeaSold = nbOfTeaSold;
            NbOfOrangeJuiceSold = nbOfOrangeJuiceSold;
        }
    }
}