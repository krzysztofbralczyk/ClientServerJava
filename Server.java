import java.net.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;


public class Server {
    private final ServerSocket serverSocket;
    private final AtomicBoolean keepRunning;
    private final Scanner consoleScanner;

    public Server(){
        keepRunning = new AtomicBoolean(true);
        serverSocket = Sockets.tryCreateServerSocket(Configuration.port);
        consoleScanner = new Scanner(System.in);
    }

    public static void main(String[] args){
        Server server = new Server();
        server.start();
    }

    private void start(){
        startListeningForClients();
        handleConsoleInput();
    }

    private void startListeningForClients(){
        ClientsAcceptor clientsAcceptor = new ClientsAcceptor(serverSocket, keepRunning);
        new Thread(clientsAcceptor).start();
        System.out.println("Server running");
    }

    private void handleConsoleInput(){
        System.out.println("Enter 'quit' if you want to close client: ");
        while(keepRunning.get()){
            String inputString = consoleScanner.next();
            if(inputString.compareTo("quit") == 0){
                keepRunning.set(false);
                Sockets.tryCloseSocket(serverSocket);
            }
        }
    }
}
