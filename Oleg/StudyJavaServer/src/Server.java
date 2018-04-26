/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pupil
 */
public class Server implements Runnable {

    int port;

    ServerSocket socketServer = null;
    BufferedReader br;
    List<Connection> connections;
    boolean working = false;

    public Server(int port) {
        this.port = port;
        connections = new ArrayList<>();
    }

    public void Start() throws IOException, InterruptedException {
        br = new BufferedReader(new InputStreamReader(System.in));

        socketServer = new ServerSocket(port);
        System.out.println("Запущен сервер на " + port);

        working = true;
        new Thread(this).start();

        do {
            String line = br.readLine();

            Send(line);
        } while (working);

        if (socketServer != null) {
            socketServer.close();
        }
        System.out.println("Bye!");
    }

    @Override
    public void run() {
        while (working) {
            try {
                Socket socket = socketServer.accept();
                Connection connection = new Connection(socket, this);
                connections.add(connection);
                SendAsServer("SERVER: Welcome! Whats your name?");
            } catch (IOException ex) {
                Close();
            }
        }
    }

    private void Close() {
        System.out.println("Закрываем сервер");
        if (socketServer.isClosed()) {
            return;
        }

        try {
            socketServer.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void Send(String data) {
        for (Connection connection : connections) {
            if (connection.sc.isClosed()) {
                continue;
            }

            connection.Send(data);
        }
    }

    private void SendAsServer(String data) {
        Send(ConsoleColors.GREEN_BACKGROUND + ConsoleColors.BLACK + "SERVER: " + ConsoleColors.BLUE_BACKGROUND + ConsoleColors.WHITE + data + ConsoleColors.RESET);
    }

    private void SendAsUser(Connection connection, String data) {
        Send(ConsoleColors.BLUE_BACKGROUND + ConsoleColors.BLACK + connection.sc.getRemoteSocketAddress() + " " + connection.username + ": " + ConsoleColors.BLUE_BACKGROUND + ConsoleColors.WHITE + data + ConsoleColors.RESET);
    }

    public void ParseInput(Connection connection, String data) {

        if (connection.username == null) {
            connection.username = data;
            SendAsServer("Hi, " + connection.username);
        } else {
            SendAsUser(connection, data);
        }
    }

}
