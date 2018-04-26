package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kiril
 */
public class OutPrinter implements Runnable {
    
    //Обьект отправляющий сообщения на сервер
    PrintWriter messageToServer;
    //Обьект читающий сообщения к клавиатуры
    BufferedReader keyBoardRead;
    //Создаём логгер
    static final Logger LOG = Logger.getLogger(Connect.class.getName());

    public OutPrinter(PrintWriter messageToServer) {
        //Присваиваем объекту передачи сообщение на сервер, 
        //занчение через конструктор
        this.messageToServer = messageToServer;
        //Образуем новый обьект ввода пользователя, с клавиатуры
        this.keyBoardRead = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        //Запускаем бесконечный цикл
        do {
            try {
                //Считываем строку, введённую пользователем
                String line = keyBoardRead.readLine();
                System.setProperty("console.encoding","Cp866");
                System.out.println(line);
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
