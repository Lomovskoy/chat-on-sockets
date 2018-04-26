
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connection implements Runnable {

    Socket sc = null;
    PrintWriter pw = null;
    BufferedReader br = null;
    boolean working = false;

    public Connection(Socket socket) {
        try {
            sc = socket;
            pw = new PrintWriter(sc.getOutputStream(), true);
            br = new BufferedReader(new InputStreamReader(sc.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }

        working = true;
        new Thread(this).start();

        System.out.println("Создано соединение c " + sc.getRemoteSocketAddress());
    }

    @Override
    public void run() {
        while (working) {
            try {
                String line = br.readLine();

                System.out.println(line);
            } catch (IOException ex) {
                Close();
            }
        }
    }

    public void Close() {
        System.out.println("Закрываем соединение");
        working = false;
        if (sc.isClosed()) {
            return;
        }
        try {
            sc.close();
        } catch (IOException ex1) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex1);
        }

    }

    public void Send(String data) {
        pw.println(data);
    }

}
