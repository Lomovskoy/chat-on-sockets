package classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс печатающий в окно чата
 * @author Kiril
 */
public class OutPrinter implements Runnable {
    
    //Обьект отправляющий сообщения на сервер
    PrintWriter messageToServer;
    //Обьект читающий сообщения c клавиатуры
    BufferedReader keyBoardRead;
    //Создаём логгер
    static final Logger LOG = Logger.getLogger(Connect.class.getName());
    //Поре харанящее логин
    String login;

    /**
     * Метод отправки сообщений на сервер
     * @param messageToServer
     * @param login
     */
    public OutPrinter(PrintWriter messageToServer, String login) {
        //Присваиваем объекту передачи сообщение на сервер, 
        //занчение через конструктор
        this.messageToServer = messageToServer;
        //Образуем новый обьект ввода пользователя, с клавиатуры
        this.keyBoardRead = new BufferedReader(new InputStreamReader(System.in));
        //Присваиваем логин из формы
        this.login = login;
    }

    @Override
    public void run() {
        messageToServer.println(login);
        //Запускаем бесконечный цикл
        do {
            try {
                //Считываем строку, введённую пользователем
                String line = keyBoardRead.readLine();
                //Еслистрока это stop
                if ("stop".equals(line)) {
                    //Печатаем сообщение от сервера
                    messageToServer.println(line);
                    //Выходим из цикла
                    break;
                }
                //Печатаем сообщение от сервера
                messageToServer.println(line);
            } catch (IOException ex) {
                LOG.log(Level.INFO, "Run error: {0}", ex);
            }

        } while (true);
        //Завершение программы
        System.exit(0);
    }
}
