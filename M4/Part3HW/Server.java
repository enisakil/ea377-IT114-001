package M4.Part3HW;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Server {
    int port = 3001;
    // connected clients
    private List<ServerThread> clients = new ArrayList<ServerThread>();

    private boolean gameActive = false;
    private int hiddenNumber = 0;


    private void start(int port) {
        this.port = port;
        // server listening
        try (ServerSocket serverSocket = new ServerSocket(port);) {
            Socket incoming_client = null;
            System.out.println("Server is listening on port " + port);
            do {
                System.out.println("waiting for next client");
                if (incoming_client != null) {
                    System.out.println("Client connected");
                    ServerThread sClient = new ServerThread(incoming_client, this);
                    
                    clients.add(sClient);
                    sClient.start();
                    incoming_client = null;
                    
                }
            } while ((incoming_client = serverSocket.accept()) != null);
        } catch (IOException e) {
            System.err.println("Error accepting connection");
            e.printStackTrace();
        } finally {
            System.out.println("closing server socket");
        }
    }
    protected synchronized void disconnect(ServerThread client) {
		long id = client.getId();
        client.disconnect();
		broadcast("Disconnected", id);
	}
    
    protected synchronized void broadcast(String message, long id) {
        if (processCommand(message, id) || processGameCommand(message, id) || processCoinToss(message, id)) {
            return;
        }
        
        // let's temporarily use the thread id as the client identifier to
        // show in all client's chat. This isn't good practice since it's subject to
        // change as clients connect/disconnect
        message = String.format("User[%d]: %s", id, message);
        // end temp identifier
        
        // loop over clients and send out the message
        Iterator<ServerThread> it = clients.iterator();
        while (it.hasNext()) {
            ServerThread client = it.next();
            boolean wasSuccessful = client.send(message);
            if (!wasSuccessful) {
                System.out.println(String.format("Removing disconnected client[%s] from list", client.getId()));
                it.remove();
                broadcast("Disconnected", id);
            }
        }
    }

    private boolean processCommand(String message, long clientId){
        System.out.println("Checking command: " + message);
        if(message.equalsIgnoreCase("disconnect")){
            Iterator<ServerThread> it = clients.iterator();
            while (it.hasNext()) {
                ServerThread client = it.next();
                if(client.getId() == clientId){
                    it.remove();
                    disconnect(client);
                    
                    break;
                }
            }
            return true;
        }
        return false;
    
    }
    private boolean processGameCommand(String message, long id) {
        String username = "User[" + id + "]";
    if (message.equalsIgnoreCase("start")) {
        if (!gameActive) {
            hiddenNumber = new Random().nextInt(100) + 1;
            gameActive = true;
            broadcast("Game started by " + username + "! Guess the number between 1-100. Start each guess with 'guess' followed by the number .", id);
        }
        return true;
    } else if (message.equalsIgnoreCase("stop")) {
        if (gameActive) {
            gameActive = false;
            broadcast("Game stopped by " + username + ".", id);
        }
        return true;
    } else if (message.startsWith("guess") && gameActive) {
        int guess = Integer.parseInt(message.split(" ")[1]);
        String guessMessage = username + " guessed " + guess;
        if (guess == hiddenNumber) {
            guessMessage += "and it's correct! The number was " + hiddenNumber;
        } else {
            guessMessage += ". That is incorrect.";
        }
        broadcast(guessMessage, id);
        return true;
    }
    return false;
}
    private boolean processCoinToss(String message, long id) {
        String username = "User[" + id + "]";
        if (message.equalsIgnoreCase("toss") || message.equalsIgnoreCase("flip")) {
            String result = (new Random().nextBoolean()) ? "heads" : "tails";
            broadcast( username + " flipped a coin and got " + result, id);
            return true;
    }
        return false;
}


    public static void main(String[] args) {
        System.out.println("Starting Server");
        Server server = new Server();
        int port = 3000;
        try {
            port = Integer.parseInt(args[0]);
        } catch (Exception e) {
            // can ignore, will either be index out of bounds or type mismatch
            // will default to the defined value prior to the try/catch
        }
        server.start(port);
        System.out.println("Server Stopped");
    }
}