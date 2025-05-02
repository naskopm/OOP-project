import java.util.Scanner;

public class CreateAutomataFromRegexCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Напишете regex симовла по който искате да създадете автомата скоби не се подържат");
        Scanner scanner = new Scanner(System.in);
        String regex = scanner.nextLine();
        CreateAutomataFromRegex(regex);
    }

    @Override
    public String getDescription() {
        return "Създаване на автомат от регулярен израз";
    }
    private void  CreateAutomataFromRegex(String regex)
    {

    }

} 