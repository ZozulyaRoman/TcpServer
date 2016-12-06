

package tcpserver;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Connection extends Thread{

    private  final int port;
    private Socket clientSocket;
    private GUI gui;
    private String ID;
    
    private BufferedReader readerFromClient;
    private PrintWriter writerToClient;
    private SimpleDateFormat sd = new SimpleDateFormat("HH:mm_dd.MM.YYYY");
    

    public Connection(Socket clientSocket, int port, GUI gui) {
        this.clientSocket = clientSocket;
        this.port = port;
        this.gui = gui;
    }
    private void initialize(){
        try {
            readerFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writerToClient = new PrintWriter(clientSocket.getOutputStream());
            String tmpID = readerFromClient.readLine();
            this.ID = (tmpID!=null&&!tmpID.trim().equals(""))?tmpID:"anonim";
            gui.sendMessToArea("user "+ID+" connected");
            sendToAll("user "+ID+" connected");
        } catch (IOException ex) {
           gui.sendMessToArea(ex.toString()); 
        }
    }  
    
    private void sendToAll(String mess){
        for(Connection c:TcpServer.connections){
            c.writerToClient.println(mess);
            c.writerToClient.flush();
            System.out.println("server send message :"+"\n"+mess+"to: "+c.ID+"\n");
            System.out.flush();
            gui.sendMessToArea("server send message :"+"\n"+mess+"to: "+c.ID+"\n");
        }
    }

    @Override
    public void run() {
        initialize();
        try {

            String clientMess = null;
            while((clientMess = readerFromClient.readLine())!=null){
                String s=this.ID+"\n"+clientMess+"\n"+sd.format(new Date())+"\n";
                System.out.println("Server get message:"+"\n"+s+"From: "+this.ID+"\n");
                System.out.flush();
                gui.sendMessToArea("Server get message:"+"\n"+s+"From: "+this.ID+"\n");
                if(TcpServer.connections.size()>0) sendToAll(s);
            }
        } catch (IOException ex) {
            System.err.println("ошибка где то в цикле метода run, наверное кто-то отключился");
            gui.sendMessToArea("ошибка где то в цикле метода run, наверное кто-то отключился");
            try {
                String errMess="user "+this.ID+" leave this chat";
                this.clientSocket.close();
                TcpServer.connections.remove(this);
                System.err.println(errMess);
                gui.sendMessToArea(errMess);
                if(TcpServer.connections.size()>0) sendToAll(errMess);
            } catch (IOException ex1) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex1);
            }
            
        } 
    }

}

