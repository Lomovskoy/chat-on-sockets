/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pupil
 */
public class Client {

    String ip;
    int port;
    Connection connection;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void Start() {

        try {
            Socket sc = new Socket(ip, port);
            connection = new Connection(sc);

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            do {
                String line = br.readLine();
                System.out.print('\r');
                connection.Send(line);
            } while (connection.working);
        } catch (IOException ex) {
            System.out.println("Ошибка соединения");
            connection.Close();
        }

    }

}
