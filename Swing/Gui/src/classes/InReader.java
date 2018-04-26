package classes;

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
    //Объект визуального интерфейса
    Connect connect;
    //Создаём логгер
    static final Logger LOG = Logger.getLogger(Connect.class.getName());

    public InReader(BufferedReader inRead, Connect connect) {
        //Присваеваем ему значение читальщика с сервера
        this.inRead = inRead;
        //Получаем объект интерфейса
        this.connect = connect;
    }

    @Override
    public void run() {
        do {            
            try {
                //Читаем строку сервера
                line = inRead.readLine();
                //Если сервер прощается с нами
                if("by-by".equals(line)){
                    connect.getFromServer(line);
                    //Выходим из цыкла
                    break;
                }
                //Выводим сообщения с сервера
                connect.getFromServer(line);
            } catch (IOException ex) {
                LOG.log(Level.INFO, "Run error: {0}", ex.toString());
            }
        } while (true);
        //Завершаем программу
        System.exit(0);
    }
    
}
