/*
 * Игровая программа, которая основанна на сокетах,
 * имитирующая игру, с сервером и пользователями,
 * где каждый модет управлять своим персонажен на клиенте.
 * Это клиентская часть
 */
package lomovskoyclient;

/**
 * Главный класс программы.
 * @author pupil
 */
public class LomovskoyClient {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        
        ClientClass clientClass = new ClientClass("localhost", 3001);
        clientClass.run();
    }
    
}
