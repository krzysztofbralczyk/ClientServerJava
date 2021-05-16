import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Streams {
    public final ObjectOutputStream out;
    public final ObjectInputStream in;
    public final BufferedInputStream bis;

    public Streams(Socket socket){
        this.out = tryGetObjectOutputStream(socket);
        this.bis = tryGetBIS(socket);
        this.in = tryGetObjectInputStream(socket);
    }

    public void trySendMessage(Message message){
        try {
            out.writeObject(message);
        }
        catch (Exception e) {
            System.out.println("trySendMessage failed");
        }
    }

    public void tryReadMessage(ArrayList<Message> listOfMessages){
        try {
            if(bis.available()>0){
                Message message = (Message)in.readObject();
                //Utils.test_printMessage(message); //TESTING
                listOfMessages.add(message);
            }
        }
        catch (Exception e) {
            System.out.println("tryReadMessage failed");
            System.exit(0);
        }
    }

    public void tryReadMessage(){
        try {
            if(bis.available()>0){
                Message message = (Message)in.readObject();
                Utils.printMessage(message);
            }
        }
        catch (Exception e) {
            System.out.println("tryReadMessage failed");
            System.exit(0);
        }
    }

    private ObjectOutputStream tryGetObjectOutputStream(Socket socket){
        ObjectOutputStream objectOutputStream = null;
        try{
            objectOutputStream = new ObjectOutputStream(tryGetOutputStream(socket));
        }
        catch (Exception e){
            System.out.println("Failed to create Object Output Stream");
            System.exit(5);
        }
        return objectOutputStream;
    }
    private BufferedInputStream tryGetBIS(Socket socket){
        BufferedInputStream bis = null;
        try{
            bis = new BufferedInputStream(tryGetInputStream(socket));
        }
        catch (Exception e){
            System.out.println("Failed to create Buffered Input Stream");
            System.exit(0);
        }
        return bis;
    }
    private ObjectInputStream tryGetObjectInputStream(Socket socket){
        ObjectInputStream objectInputStream = null;
        try{
            objectInputStream = new ObjectInputStream(tryGetBIS(socket));
        }
        catch (Exception e){
            System.out.println("Failed to create Object Input Stream");
            System.exit(0);
        }
        return objectInputStream;
    }
    private OutputStream tryGetOutputStream(Socket socket){
        OutputStream outputStream = null;
        try{
            outputStream = socket.getOutputStream();
        }
        catch (Exception e){
            System.out.println("Failed to get output stream!");
            e.printStackTrace();
            System.exit(0);
        }
        return outputStream;
    }
    private InputStream tryGetInputStream(Socket socket){
        InputStream inputStream = null;
        try{
            inputStream = socket.getInputStream();
        } catch (Exception e){
            System.out.println("Failed to get input stream");
            System.exit(0);
        }
        return inputStream;
    }
}