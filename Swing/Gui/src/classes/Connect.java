package classes;

import gui.Gui;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

/**
 * Класс соединения c сервером
 * @author Lomovskoy
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

    /**
     * Метод для соединения с сервером
     * @param ip
     * @param port
     * @param login
     * @param gui
     */
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
            messageFromServer = new BufferedReader(new InputStreamReader(connections.getInputStream(), "UTF8"));
            //Создаём обьект сообщения для сервера
            messageToServer = new PrintWriter(new OutputStreamWriter(connections.getOutputStream(), StandardCharsets.UTF_8),true);
            //Создаём обьект считывания с клавиатуты
            kbRead = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException ex) {
            //LOG.log(Level.INFO, "Connect error: {0}", ex.toString());
            getFromServer("Сервер недоступен");
        }
        
        //ОтправитьНаСервер(login);
    }

    /**
     * Метод чтения с сервера запускаемый в новом потоке
     */
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
        String ch = message.substring(0,1);
        if("0".equals(ch)){
            message = message.replace("0", "");
            gui.write(message);
        }
        else if("1".equals(ch)){
            message = message.replace("1", "");
            message = message.replace("|", "\n");
            //String[] isbnParts = isbn.split("-");
            gui.usrtListWrite(message);
        }else{
            gui.write("Неизвестный формат ответа от сервера.");
        }
    }
    /**
     * Отправить на сервер
     * @param message 
     */
    public void sendToServer(String message) {
        messageToServer.println(message);
    }
    /**
     * Завершение работы по крестику
     */
    public void outUser(){
        messageToServer.println("stop");
    }
}
