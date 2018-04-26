package lomovskoyserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * Класс сервера 
 * в нём находится основной функционал работы с клиентом
 * @author pupil
 */
public class ServerClass {
    //Сокет который будет использовать сервер
    private ServerSocket connectionServer;
    //Порт который будет слушать сервер
    private Integer port;
    //Флаг который определяет работает ли сервер
    private Boolean working;
    //Сокет конкретного соединения с клиентом.
    private Socket connections;
    //Отсылает сообщения клиенту.
    private PrintWriter messageFromClient;
    //Получает сообщения от клиента
    private BufferedReader messageToClient;
    //Имя для приветствия клиента
    private String firstname;
    //Строка переданная от клиента
    private String line;
    //Создаём логгер
    static final Logger LOG = Logger.getLogger(ServerClass.class.getName());
    
    /**
     * Конструктор класса
     * @param port принимает номер порта
     * @throws java.io.IOException
     */
    public ServerClass(Integer port) throws IOException {
        //Устанавливаем порт
        this.port = port;
        //Устанавливаем сервер соединений по порту
        connectionServer = new ServerSocket(port);
        //Ставим флаг в работу
        working = true;
        //Выводим сообщение
        LOG.info("Сервер слушает порт: " + port);
    }

    /**
     * Главный класс работы сервера.
     * @throws IOException 
     */
    public void run() throws IOException{
        try {
            //Устанавливаем соединение по сокету
            connections = connectionServer.accept();
            //Инициализируем объет получающий сообщение от клиента
            messageFromClient = 
                    new PrintWriter(connections.getOutputStream(),true);
            //Инициализируем объет отправляющий сообщение клиенту
            messageToClient = new BufferedReader(
                    new InputStreamReader(connections.getInputStream()));
            //Запрашиваем имя у клиента
            messageFromClient.println("Введите имя: ");
            //Ждём ответа от клиента
            firstname = messageToClient.readLine();
            LOG.info("Сервер поулчил: " + firstname);
            //Отправляем сообщение приветствия
            messageFromClient.println("Привет " + firstname + "|Пиши ещё: ");
            LOG.info("Сервер отправил: " + "Привет " + firstname + "|Пиши ещё: ");
            
            do {
                //Ждём ответа от клиента
                line = messageToClient.readLine();
                LOG.info("Сервер поулчил: " + line);
                //Если пришла команда стоп закончить работу сервера
                if (line.equals("stop")) {
                    //Отправляем сообщение
                    messageFromClient.println("Сервер закончил работу");
                    LOG.info("Сервер закончил работу");
                    break;
                }
                //Отправляем сообщение
                messageFromClient.println(firstname + " сервер получил: " + 
                        line + "|Пиши ещё: ");
                LOG.info("Сервер отправил: " + firstname + " сервер получил: " + 
                        line + "|Пиши ещё: ");
            } while (working);    
        }finally{
            //Закрыть все ресурсы
            if(messageToClient != null) messageToClient.close();
            if(messageFromClient != null) messageFromClient.close();
            if(connections != null) connections.close();
        }
    }
}
