import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientsAcceptor implements Runnable{
    private final AtomicBoolean keepRunning;
    private final ServerSocket serverSocket;

    private final ArrayList<ClientRunnable> listOfClients;

    public ClientsAcceptor(ServerSocket serverSocket, AtomicBoolean keepRunning) {
        this.keepRunning = keepRunning;
        this.serverSocket = serverSocket;

        this.listOfClients = new ArrayList<>();
    }

    @Override
    public void run() {
        while(keepRunning.get()) {
            Socket clientSocket = tryAcceptClient();
            ClientRunnable clientRunnable = new ClientRunnable(clientSocket, keepRunning);
            listOfClients.add(clientRunnable);
            new Thread(clientRunnable).start();
        }
    }

    private Socket tryAcceptClient(){
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch(SocketException e){
            System.out.println("Server socket closed");
            System.exit(0);
        } catch (Exception e) {
            System.out.println("Failed to accept connection from new client");
            System.exit(0);
        }
        return clientSocket;
    }
}
