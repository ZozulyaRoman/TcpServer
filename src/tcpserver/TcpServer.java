
package tcpserver;




import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class TcpServer {
    private static final int DEFAULT_PORT = 6666;
    protected static volatile Set<Connection> connections = new HashSet<>();

    public static void main(String[] args) {
        GUI gui = new GUI();
        int port = DEFAULT_PORT;
        if(args.length>0){
            port = Integer.parseInt(args[0]);
        }
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(port);
        } catch (IOException ex) {
            System.err.println("port "+port+" is busy");
            System.exit(-1);
        }
        
         while(true){
             Socket clientSocket=null;
             try {
                 clientSocket = ss.accept();
            } catch (IOException ex) {
                System.err.println("Error connect with port: "+port);
                System.exit(-1);
            }
             
            Connection connection = new Connection(clientSocket, port, gui);
            connections.add(connection);
            connection.start();
         }     
    } 
}
