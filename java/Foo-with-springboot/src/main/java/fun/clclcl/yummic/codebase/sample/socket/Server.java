package fun.clclcl.yummic.codebase.sample.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int PORT = 5000;

    public static void main(String args[]) {
        ServerSocket serverSocket = null;
        Socket socket = null;

        //starts the server
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started");
            System.out.println("Waiting for a client ...\n");
        } catch (IOException e) { System.out.println(e); }

        //while loop to accept multiple clients
        int count = 1;
        while(true) {
            try {
                socket = serverSocket.accept();
                System.out.println("Client " + count + " accepted!");
                count++;
            } catch (IOException e) { System.out.println(e); }

            //starts the server thread
            new EchoThread(socket).start();
        }

    }
}

class EchoThread extends Thread {
    //*****What I add begin.
    private static List<Socket> socketList = new ArrayList<>();
    //*****What I add end.

    private Socket socket;

    //constructor
    public EchoThread(Socket clientSocket) {
        this.socket = clientSocket;
        socketList.add(socket);
    }

    @Override
    public void run() {
        DataInputStream inp = null;

        try {
            inp = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            //print whatever client is saying as long as it is not "Over"
            String line = "";
            while (!line.equals("Over")) {
                try {
                    line = inp.readUTF();
                    System.out.println(line);

                    //*****What I add begin.
                    sendMessageToClients(line);
                    //*****What I add end.
                } catch (IOException e) { System.out.println(e); break;}
            }

            //closes connection when client terminates the connection
            System.out.print("Closing Connection");
            socket.close();
        } catch (IOException e) { System.out.println(e); }
    }

    //*****What I add begin.
    private void sendMessageToClients(String line) throws IOException {
        for (Socket other : socketList) {
            if (other == socket) {
                continue;//ignore the sender client.
            }
            DataOutputStream output = new DataOutputStream(other.getOutputStream());
            output.writeUTF(line);
        }
    }
    //*****What I add end.
}
