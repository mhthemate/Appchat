/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author chien
 */
public class server extends Thread {
    private static String message="";
    private int port ;
    private  ArrayList<Socket> listSoc;
    public server(int port){
        this.port = port;
    }
    
    @Override
    public void run() {
        ServerSocket listener = null;
        try {
            listSoc=new ArrayList<Socket>();
            listener = new ServerSocket(this.port);
            while (true) {
                final Socket socketOfServer = listener.accept();
                listSoc.add(socketOfServer);
                if(listSoc.size() >=2){
                    listener.close();
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                        Scanner in=new Scanner(socketOfServer.getInputStream());
                        while(in.hasNextLine()) {
                            message=in.nextLine();
                            for (int i = 0; i < listSoc.size(); i++) {
                                if(listSoc.get(i)==null)
                                    listSoc.remove(listSoc.get(i));
                                else if(!listSoc.get(i).equals(socketOfServer)){
                                    PrintWriter out=new PrintWriter(listSoc.get(i).getOutputStream(),true);
                                    out.println(message);
                                }
                            }
                        }
                        }catch (Exception e ) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
