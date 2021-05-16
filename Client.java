import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client {
    private final Socket socket;
    private final Streams streams;
    private final AtomicBoolean programOn;
    private final Scanner consoleScanner;

    public Client(){
        this.programOn = new AtomicBoolean(true);
        this.socket = Sockets.tryCreateSocket(Configuration.port,Configuration.ip);
        this.streams = new Streams(socket);
        this.consoleScanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    private void start() {
        startListeningForMessages();
        handleConsoleInput();
    }

    private void startListeningForMessages(){
        ClientListener clientListener = new ClientListener(streams, programOn);
        new Thread(clientListener).start();
    }

    private void handleConsoleInput(){
        System.out.println("Enter text message or 'quit', and press enter ");
        System.out.println("After message, enter in how many seconds you want to get your message back");

        while(programOn.get()){
            String inputString = consoleScanner.nextLine();
            if(inputString.compareTo("quit") == 0){
                programOn.set(false);
                Sockets.tryCloseSocket(socket);
            }
            else {
                int inputInt = tryScanInt();
                if(inputInt <= 5) continue; //don't like it, how to make thrown exception as a trigger for this
                Message message = new Message(inputString, inputInt);
                streams.trySendMessage(message);
                System.out.println("Message send, you can input new message");
            }
        }
    }

    private int tryScanInt(){
        int inputInt = -1;

        while(inputInt <= 5){
            try{
                inputInt = scanInt();
            } catch (IncorrectTimeException e){
                System.out.println(e.message);
            } catch (InputMismatchException e){
                System.out.println("Please enter time in seconds, enter time again: ");
                consoleScanner.nextLine(); //clean input buffer
            }
        }

        return inputInt;
    }

    private int scanInt() throws IncorrectTimeException, InputMismatchException{
        int inputInt = consoleScanner.nextInt();
        consoleScanner.nextLine(); //clean input buffer
        if(inputInt <= 5) throw new IncorrectTimeException("Please set timer to minimum of 6 second, enter time again: ");
        return inputInt;
    }

}
