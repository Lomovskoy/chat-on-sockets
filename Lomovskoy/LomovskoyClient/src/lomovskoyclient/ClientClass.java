package lomovskoyclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Класс обрабатывающий логику клиента
 * @author pupil
 */
public class ClientClass {
    //Ip для соединения с сервером
    String ip;
    //Порт для моединения с сервером
    Integer port;
    //Сокет конкретного соединения с сервером.
    Socket connections;
    //Получает сообщения от сервера
    BufferedReader messageToClient;
    //Отсылает сообщения серверу.
    PrintWriter messageFromServer;
    //Сообщение введённое пользхователем
    BufferedReader keyBoard;
    //Строка переданная от сервера
    String line;
    //Строка сотправляемая на сервер
    String clientString;
    //Флаг который определяет работает ли сервер
    private Boolean working;
                    
    public ClientClass(String ip, Integer port) throws IOException {
        //Устанавливаем ip
        this.ip = ip;
        //Устанавливаем порт
        this.port = port;
        //Ставим флаг в работу
        working = true;
        //Устанавливаем канал соединений по порту
        connections = new Socket(ip, port);
        //Инициализируем объет получающий сообщение от сервера
        messageToClient = new BufferedReader(new InputStreamReader(connections.
                getInputStream()));
        //Объект который получает сообщения с клавиатуры
        keyBoard = new BufferedReader(new InputStreamReader(System.in));
        //Инициализируем объет получающий сообщение для сервера
        messageFromServer = new PrintWriter(connections.getOutputStream(),true);
    }
    
    public void run() throws IOException{
        //Получаем приветствие от сервера
        line = messageToClient.readLine();
        do {
            //Делим входящую строку по символу на несколько
            line = line.replace('|', '\n');
            //Выводим сообщение для клиента
            System.out.println(line);
            //Получаем сообщение от клиента
            clientString = keyBoard.readLine();
            //Отсылаем сообщение на сервер
            messageFromServer.println(clientString);
            //Получаем сообщение с сервера
            line = messageToClient.readLine();
            //Если сервер отватился
            if (line == null) {
                //Выводим ошибку и закрываем программы
                System.out.println("Serwer Error.");
                break;
            }
            //Если отправленно сообщение об окончании 
            else if(line.equals("Сервер закончил работу")){
                //Выдаём сообщение и завершаем программу
                System.out.println(line);
                break;
            }
        } while (working);
    }
}
