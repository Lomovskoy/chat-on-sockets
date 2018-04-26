/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pupil
 */
public class StudyJavaServer {

    static Server server;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            server = new Server(3001);
            server.Start();
        } catch (IOException ex) {
            Logger.getLogger(StudyJavaServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(StudyJavaServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
