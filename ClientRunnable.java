import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientRunnable implements Runnable{
    private final AtomicBoolean keepRunning;
    private final Socket clientSocket;

    private final Streams streams;
    private final ArrayList<Message> listOfMessages;


    public ClientRunnable(Socket socket, AtomicBoolean keepRunning) {
        this.clientSocket = socket;
        this.keepRunning = keepRunning;

        this.streams = new Streams(socket);

        this.listOfMessages = new ArrayList<>() {
            public boolean add(Message m) {
                super.add(m);
                Collections.sort(listOfMessages, new DateComparator());
                return true;
            }
        };

    }

    @Override
    public void run(){
        while(keepRunning.get()){
            Utils.sleep(1);
            streams.tryReadMessage(listOfMessages);
            checkTimerAndSendMessages();
        }
        Sockets.tryCloseSocket(clientSocket);
    }

    private void checkTimerAndSendMessages(){
        if(listOfMessages.size() > 0 && listOfMessages.get(0).timeToSendReached()){
            streams.trySendMessage(listOfMessages.get(0));
            listOfMessages.remove(0);
        }
    }
}
