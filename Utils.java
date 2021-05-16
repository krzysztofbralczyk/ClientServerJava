import java.util.concurrent.TimeUnit;


public class Utils {
    public static void sleep(int delay){
        try {
            TimeUnit.SECONDS.sleep(delay);
        } catch (Exception e){
            System.out.println("How can sleep go wrong!");
        }
    }
    public static void printMessage(Message message){
        //System.out.println("|"+message+"|");
        System.out.println("|"+message.time+"|");
        System.out.println("|"+message.mainMessage+"|");
    }
}

