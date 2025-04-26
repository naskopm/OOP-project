public class CreateAutomataCommand implements Command {
    @Override
    public void execute() {
        new Automata();
    }

    @Override
    public String getDescription() {
        return "Създаване на нов автомат";
    }
} 