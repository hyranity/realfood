package Exception;

public class IDGenerationException extends Exception {
    public IDGenerationException(){
        super("Error: ID cannot be generated.");
    }
}
