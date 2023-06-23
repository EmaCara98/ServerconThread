package com.ingconti;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

// to test against console:
//  /usr/bin/nc 127.0.0.1 1234
// and type in console: server will receive.
// it will NOT block socket (for now..) when timeout.

public class ServerMain
{
    static final int portNumber = 1234;
    static final int maxRetries = 10;



    public static void main( String[] args )
    {
        System.out.println("Server started!");

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Socket clientSocket = null;

        while(true)
        {
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ClientHandler clientHandler = new ClientHandler(clientSocket);
            ClientManager.getInstance().add(clientHandler);

            Thread thread = new Thread(clientHandler);
            thread.start();


        }
    }
}

