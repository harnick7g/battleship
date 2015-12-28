/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hundirlaflota.srv;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author En clase
 */
public class HundirLaFlotaSRV {
    ServerSocket serverSocket;
    final Dispatcher dispatcher;
    final int localPort;
    boolean seguir = true;
    Socket socket = null;
    final ConcurrentLinkedQueue<Socket> queueSockets;
    final String softwareID = "HundirLaFlota Server V 0.1";
    final String clientID = "HundirLaFlota Cliente V 0.1";

    /**
     * @param localPort
     */
    public HundirLaFlotaSRV ( int localPort ) {
        this.localPort = localPort;

        queueSockets = new ConcurrentLinkedQueue<>();
        dispatcher = new Dispatcher( queueSockets );

        try {
            serverSocket = new ServerSocket( localPort );
        } catch ( IOException ex ) {
            serverSocket = null;
            Logger.getLogger( HundirLaFlotaSRV.class.getName() ).log( Level.SEVERE, null, ex );
        }
    }

    public void loop () {
        dispatcher.start();
        try {
            while ( seguir ) {
                socket = serverSocket.accept();
                queueSockets.add( socket );
            }
        } catch ( IOException ex ) {
            Logger.getLogger( HundirLaFlotaSRV.class.getName() ).log( Level.SEVERE, null, ex );
        }
        dispatcher.finish();
    }

    public void terminarAplicacion () {
        seguir = false;
    }

    public static void main ( String[] args ) {
        // TODO code application logic here
        HundirLaFlotaSRV mainLoop = new HundirLaFlotaSRV( 42320 );
        mainLoop.loop();
    }

}
