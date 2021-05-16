import java.io.Serializable;
import java.time.*;
import java.time.temporal.ChronoUnit;

public class Message implements Serializable {
    public String mainMessage;
    public LocalTime time;


    public Message(String mainMessage, int time) {
        this.mainMessage = mainMessage;
        this.time = LocalTime.now().plus(time, ChronoUnit.SECONDS);
    }

    public boolean timeToSendReached(){
        return time.isBefore(LocalTime.now());
    }

}
