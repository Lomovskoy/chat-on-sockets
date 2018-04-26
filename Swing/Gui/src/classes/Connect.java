package classes;

import gui.Gui;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс соединения c сервером
 *
 * @author Kiril
 */
public class Connect {

    //Ip для подключения к серверу
    String ip;
    //Порт по которому будет работать программа
    int port;
    //login пользователя
    String login;
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
    //Объект визуального интерфейса
    Gui gui;
    
    OutPrinter outPrinter;

    public Connect(String ip, int port, String login, Gui gui) {
        //Получаем Ip с формы
        this.ip = ip;
        //Получаем port с формы
        this.port = port;
        //Получаем login с формы
        this.login = login;
        //Получаем объект интерфейса
        this.gui = gui;

        try {
            //Устанавливаем соединение с сервером
            connections = new Socket(this.ip, this.port);
            //Создаём обьект сообщения от сервера
            messageFromServer = new BufferedReader(new InputStreamReader(connections.getInputStream()));
            //Создаём обьект сообщения для сервера
            messageToServer = new PrintWriter(connections.getOutputStream(), true);
            //Создаём обьект считывания с клавиатуты
            kbRead = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException ex) {
            //LOG.log(Level.INFO, "Connect error: {0}", ex.toString());
            getFromServer("Сервер недоступен");
        }
        
        //ОтправитьНаСервер(login);
    }

    public void run() {
        //Создаём новый обьект чтения от сервера
        InReader inReader = new InReader(messageFromServer, this);
        //Запукаемп его работы в новом потоке
        new Thread(inReader).start();
        
    }
    /**
     * Полуить с сервера
     * @param message 
     */
    public void getFromServer(String message) {
        gui.write(message);
    }
    /**
     * Отправить на сервер
     * @param message 
     */
    public void sendToServer(String message) {
        messageToServer.println(message);
    }
}
