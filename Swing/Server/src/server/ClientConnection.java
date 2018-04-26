package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * Класс реализующий соединение с клиентом
 * @author Kirill
 */
public class ClientConnection implements Runnable {
    //Сокет конкретного соединения с клиентом.
    Socket connections;
    //Отсылает сообщения клиенту.
    PrintWriter messageFromClient;
    //Получает сообщения от клиента
    BufferedReader messageToClient;
    //Строка переданная от клиента
    String line = "";
    //Объект Сервера
    Server server;
    //Имя для приветствия клиента
    String name = "";
    //Создаём логгер
    static final Logger LOG = Logger.getLogger(ClientConnection.class.getName());

    public ClientConnection(Socket socket, Server work) throws IOException {
        //Устанавливаем соединение по конкретному сокету
        this.connections = socket;
        //Устанавливаем флаг работы
        this.server = work;
        //Отсылаем сообщение клиенту
        this.messageFromClient = new PrintWriter(this.connections.getOutputStream(), true);
        //Получаем ответ от клиента
        this.messageToClient = new BufferedReader(new InputStreamReader(connections.getInputStream()));
        //Создаём объект нового потока
        Thread thClient = new Thread(this);
        //Запускаем процесс в новом потоке
        thClient.start();
    }

    @Override
    public void run() {
        //запускаем сервер в новом потоке
        server.add(this);
    }

}
