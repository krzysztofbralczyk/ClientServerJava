import java.net.ServerSocket;
import java.net.Socket;

public class Sockets {
    public static Socket tryCreateSocket(int port, String ip){
        Socket socket = null; // <- I don't want to introduce null, but have no better idea :(
        try {
            socket = new Socket(ip,port);
        } catch(Exception e){
            System.out.println("Failed to create client socket, terminating program");
            System.exit(0);
        }
        return socket;
    }
    public static ServerSocket tryCreateServerSocket(int port){
        ServerSocket socket = null; // <- ugly but I have no better idea
        try {
            socket = new ServerSocket(port);
        }
        catch(Exception e){
            System.out.println("tryCreateServerSocket failed, terminating program");
            System.exit(0);
        }
        return socket;
    }
    public static void tryCloseSocket(Object socket){
        try{
            if(socket instanceof Socket) ((Socket)socket).close();
            else if( socket instanceof ServerSocket) ((ServerSocket)socket).close();
            else{
                System.out.println("tryCloseServerSocket takes only Socket and ServerSocket as parameters");
                System.exit(0);
            }
        } catch (Exception e){
            System.out.println("tryClose Server Socket - Failed to close server socket");
        }
    }
}
