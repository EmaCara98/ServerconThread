package com.ingconti;

import java.util.ArrayList;
import java.util.List;

public final class ClientManager {

    private static ClientManager INSTANCE;

    private List<ClientHandler> clientList = new ArrayList<>();

    private ClientManager() {
    }

    public static ClientManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ClientManager();
        }

        return INSTANCE;
    }

    void add(ClientHandler clientHandler)
    {
        this.clientList.add(clientHandler);
    }

    void remove(ClientHandler clientHandler)
    {
        this.clientList.remove(clientHandler);
    }

    int nOfClients()
    {
        return this.clientList.size();
    }

    void broadcast(String s, ClientHandler client)
    {
        for(ClientHandler c : clientList)
        {
            if(!c.equals(client)) //evito di inviare il messaggio a chi lo ha inviato
            {
                c.write(s);
            }

        }
    }
}