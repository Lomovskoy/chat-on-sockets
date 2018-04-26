package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Kiril
 */
public class Connect {
    //Ip для подключения к серверу
    String ip = "localhost";
    //Порт по которому будет работать программа
    int port = 3001;
    //Сокет конкретного соединения с сервером.
    Socket connections;
    //Получает сообщения от сервера
    BufferedReader messageFromServer;
    //Отсылает сообщения серверу.
    PrintWriter messageToServer;
    //Сообщение введённое пользхователем
    BufferedReader kbRead;
    //Строка переданная от сервера
    String line = "";
    //Строка сотправляемая на сервер
    String clientStr = "";
    //Создаём логгер
    static final Logger LOG = Logger.getLogger(Connect.class.getName());
    
    public Connect() {
        try {
            //Устанавливаем соединение с сервером
            connections = new Socket(ip, port);
            //Создаём обьект сообщения от сервера
            messageFromServer = new BufferedReader(new InputStreamReader(connections.getInputStream()));
            //Создаём обьект сообщения для сервера
            messageToServer = new PrintWriter(connections.getOutputStream(),true);
            //Создаём обьект считывания с клавиатуты
            kbRead = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException ex) {
            LOG.log(Level.INFO, "Connect error: {0}", ex.toString());
        }
    }
    
    public void run(){
        //Создаём новый обьект чтения от сервера
        InReader inReader = new InReader(messageFromServer);
        //Запукаемп его работы в новом потоке
        new Thread(inReader).start();
        //Создаём объект отправки на сервер
        OutPrinter outPrinter = new OutPrinter(messageToServer);
        //Запускаем его в новом потоке
        new Thread(outPrinter).start();
    }
    
}
