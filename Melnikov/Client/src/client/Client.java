/*
 * Класс который реализует работу клиента, клиент подключается к серверу
 * перелельно могут работать несколько клиентов
 */
package client;

/**
 * Класс запуска клиентвской программы
 * @author Kiril
 */
public class Client{

    /**
     * @param args аргумент командной строки
     */
    public static void main(String[] args) {
        //Устанавливаем кодировку
        System.setProperty("console.encoding","Cp866");
        //Создание объекта подключение
        Connect app = new Connect();
        //Запускаем подключение
        app.run();
    }
    
}
