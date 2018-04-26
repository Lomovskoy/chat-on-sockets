package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс считывающий с сервера
 * @author Kiril
 */
public class InReader implements Runnable{
    //Создаём объект чтения 
    BufferedReader inRead;
    //Создаём строку чтения с сервера
    String line;
    //Создаём логгер
    static final Logger LOG = Logger.getLogger(Connect.class.getName());

    public InReader(BufferedReader inRead) {
        //Присваеваем ему значение читальщика с сервера
        this.inRead = inRead;
    }

    @Override
    public void run() {
        do {            
            try {
                //Читаем строку сервера
                line = inRead.readLine();
                //Если сервер прощается с нами
                if("by-by".equals(line)){
                    //Выводим присланное сервером
                    System.out.println(line);
                    //Выходим из цыкла
                    break;
                }
                //Выводим сообщения с сервера
                System.out.println(line);
            } catch (IOException ex) {
                LOG.log(Level.INFO, "Run error: {0}", ex.toString());
            }
        } while (true);
        //Завершаем программу
        System.exit(0);
    }
    
}
