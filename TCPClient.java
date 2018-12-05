import org.json.simple.JSONObject;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class TCPClient{
    /**
     * Method responsible for sending data to the server
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException{
        InetAddress host = InetAddress.getLocalHost();
        Socket socket;
        ObjectOutputStream objectOutputStream;
        ObjectInputStream objectInputStream;

        socket = new Socket(host.getHostName(), 9876);

        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("Sending request to Socket Server");

        JSONObject object = new JsonConverter().convert();
        objectOutputStream.writeObject(object);

        objectInputStream = new ObjectInputStream(socket.getInputStream());
        String message = (String) objectInputStream.readObject();
        System.out.println("Message: " + message);

        objectInputStream.close();
        objectOutputStream.close();
    }
}