/*
 * Игровая программа, которая основанна на сокетах,
 * имитирующая игру, с сервером и пользователями,
 * где каждый модет управлять своим персонажен на клиенте.
 * Это серверная часть
 */
package lomovskoyserver;


/**
 * Главный класс программы.
 * @author imxo
 */
public class LomovskoyServer {

    /**
     * Подключаем и активирует сокет.
     * @param args задаются с командной строки.
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        //Передаём порт при инициализации класса сервера
        ServerClass sc = new ServerClass(3001);
        //Вызываем метод запуска сервера.
        sc.run();
    }

}
