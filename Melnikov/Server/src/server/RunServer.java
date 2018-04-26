/*
 * Это программа имитирующая работу сервера, сервер модет рабоать 
 * в многопоточном режиме, одновременно выводя сообщения разных пользователей
 * в общий чат.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс включения сервера и добавления нового пользователя.
 * @author Kirill
 */
public class RunServer {
    
    //Создаём логгер
    static final Logger LOG = Logger.getLogger(RunServer.class.getName());

    /**
     * @param args аргумент командной строки
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        //Создаём порт который будет слушать сервер
        int port = 3001;
        //Условие работы цыкла
        Boolean working = true;
        //Создаём сокет для работы сервера
        ServerSocket ss = new ServerSocket(port);
        //Пишем в лог информацию от сервера

        LOG.log(Level.INFO, "Server listens port: {0}", port);
        //Создаём обьект сервера
        Server server = new Server();
        //Создаём новый поток для выполнения, передаём туда аргумент роботы
        Thread myWork = new Thread(server, "work");
        //Запускаем в новом потоке сервер
        myWork.start();
        //Запускаем цикл
        while (working) {
            //Создаём новое соединение для клиена
            new ClientConnection(ss.accept(), server);
        }

    }

}
