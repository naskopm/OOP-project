class EmptyLanguageException extends Exception {
    public EmptyLanguageException(String message) {
        super(message);
    }
}
public class Automata{

    int [] transitions;
    int id;
    String alphabet;
    String[] language;
    public Automata(int trainsitions [], int id, String alphabet, String [] language) throws EmptyLanguageException
    {
        this.transitions = trainsitions;
        this.id = id;
        if (checkEmptyLanguage(language))
            this.alphabet = alphabet;
        else
        {
            throw new EmptyLanguageException("Language can't be empty");
        }
        this.language = language;

    }
    public Boolean checkEmptyLanguage(String [] language)
    {
        if (language.length == 0)
        {
            return false;
        }
        return true;
    }



}
