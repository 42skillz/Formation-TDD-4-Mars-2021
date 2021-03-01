package coffee.machine.domain;

public class DrinkInstructionFailed extends DrinkInstruction {
    private final String errorMessage;

    public DrinkInstructionFailed(String message) {
        this.errorMessage = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
