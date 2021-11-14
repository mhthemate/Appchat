/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 *
 * @author chien
 */
public class client {
    
    public Socket clientSocket;
    private ArrayList<String> message = new ArrayList<String>();
    private boolean connectable = true;
    
    //getter
    public boolean getConnectable(){
        return connectable;
    }
    //setter
    public void setConnectable(boolean connectable){
        this.connectable = connectable;
    }
    //constructor
    public void setClientSocket(int port){
        try {
            clientSocket = new Socket("localhost", port);
        }catch(Exception e) {
            setConnectable(false);
        }
    }
    /**
     * This method is send a String message to the Server by a Thread
     */
    public void sendMessage(String msg){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PrintWriter out=new PrintWriter(clientSocket.getOutputStream(),true);
                    out.println(msg);
                } catch (Exception e) {
                    System.err.println("Trying to connect to unknown host: " + e);
                    setConnectable(false);
                } 
            }
        }).start();
    }

}
