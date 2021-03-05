namespace CoffeeMachine.Tests.Domain
{
    public interface INotifyViaEMail
    {
        void NotifyMissingDrink(string drink);
    }
}