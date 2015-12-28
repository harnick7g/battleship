/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hundirlaflota.srv;

import Protocolo.Interlocutor;
import Protocolo.Message;
import Protocolo.MessageType;
import XML.XML;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 * Clase que despacha las conexiones entrantes por el Socket.
 *
 * @author En clase
 */
public class Dispatcher extends Thread {
    final private ConcurrentLinkedQueue<Socket> queueSockets;
    private boolean sigue = true;
    private BufferedReader br = null;
    private String buffer = null;
    private Message message = null;
    private Thread loginThread = null;
    private Thread registerThread = null;

    /**
     * Construye un objeto Dispatcher.
     *
     * @param queueSockets Cola de sockets para ser despachada.
     */
    public Dispatcher ( ConcurrentLinkedQueue<Socket> queueSockets ) {
        this.queueSockets = queueSockets;
    }

    /**
     * Solicitud para finalizar el proceso de la lista de sockets (parar el
     * dispatcher).
     */
    public void finish () {
        sigue = false;
    }

    @Override
    public void run () {
        while ( sigue || !queueSockets.isEmpty() ) {
            Socket clientSocket;
            if( !queueSockets.isEmpty() ){
                clientSocket = queueSockets.poll();
                InputStream streamInput = null;
                OutputStream streamOutput;
                try {
                    streamInput = clientSocket.getInputStream();
                    streamOutput = clientSocket.getOutputStream();
                } catch ( IOException ex ) {
                    Logger.getLogger( Dispatcher.class.getName() ).log( Level.SEVERE, null, ex );
                }
                //crear Message a partir de la cadena XML
                //    leida del stream
                br = new BufferedReader(new InputStreamReader(streamInput));
                buffer = null;
                try {
                    buffer = br.readLine();
                } catch (IOException ex) {
                    Logger.getLogger(Dispatcher.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                message = null;
                try {
                    message = Message.fromXML(buffer);
                } catch (ParserConfigurationException ex) {
                    Logger.getLogger(Dispatcher.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SAXException ex) {
                    Logger.getLogger(Dispatcher.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Dispatcher.class.getName()).log(Level.SEVERE, null, ex);
                } catch (TransformerException ex) {
                    Logger.getLogger(Dispatcher.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if( message.getType() == MessageType.LOGIN){
                    try {
                        loginThread = new Thread(new LoginThread( clientSocket, message ));
                        loginThread.start();
                    } catch (SQLException ex) {
                        Logger.getLogger(Dispatcher.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SocketException ex) {
                        Logger.getLogger(Dispatcher.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    loginThread = null;
                    
                }else if( message.getType() == MessageType.REGISTER){
                    try {
                        registerThread = new Thread(new RegisterThread( clientSocket, message ));
                        registerThread.start();
                    } catch (SQLException ex) {
                        Logger.getLogger(Dispatcher.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SocketException ex) {
                        Logger.getLogger(Dispatcher.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    registerThread = null;
                }
                //habria que guardarlo en una lista
            }
        }
        // Aquí hay que finish todos los sub-hilos que ponga en marcha este objeto.
    }

}
