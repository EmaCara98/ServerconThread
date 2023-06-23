package com.ingconti;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable{

    private Socket clientSocket = null;
    private InetAddress address;
    private int port;
    private PrintWriter out;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;

        //Indirizzo e porta del client che si connette
        address = clientSocket.getInetAddress();
        port = clientSocket.getPort();

        System.out.println("Connected: " + address + "with port: " + port);
    }

    Boolean readLoop(BufferedReader in,  PrintWriter out ){
        // waits for data and reads it in until connection dies
        // readLine() blocks until the server receives a new line from client
        String s = "";

        try {
            while ((s = in.readLine()) != null) {
                System.out.println(s);

                //ricevo messaggio dal client e lo mando a tutti i client connessi con BROADCAST
                ClientManager.getInstance().broadcast(s, this);

            }

            System.out.println("Disconnected: " + address + "with port: " + port);
            ClientManager.getInstance().remove(this);
            System.out.println("Now we have " + ClientManager.getInstance().nOfClients() + " connected client");

            return true;

        } catch (IOException e) {
            System.out.println("Forcing disconnection for: " + address + "with port: " + port);
        }

        return false;
    }

    void handle()
    {
        out = null; // allocate to write answer to client.
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            readLoop(in, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        handle();
    }

    void write(String s)
    {
        out.println(s);
        out.flush();
    }
}
