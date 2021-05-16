import java.util.concurrent.atomic.AtomicBoolean;

public class ClientListener implements Runnable{
    private final AtomicBoolean programOn;
    private final Streams streams;
    public ClientListener(Streams streams, AtomicBoolean programOn){
        this.programOn = programOn;
        this.streams = streams;
    }

    @Override
    public void run(){
        while(programOn.get()){
            streams.tryReadMessage();
            Utils.sleep(1);
        }
    }
}
