public class IncorrectTimeException extends Exception{
    public final String message;

    public IncorrectTimeException(String message){
        this.message = message;
    }
}
