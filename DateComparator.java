import java.util.Comparator;

public class DateComparator implements Comparator<Message> {

    @Override
    public int compare(Message m1, Message m2) {
        return m1.time.compareTo(m2.time);
    }
}