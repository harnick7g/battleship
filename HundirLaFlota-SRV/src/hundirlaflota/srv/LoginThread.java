/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hundirlaflota.srv;

import Protocolo.Data;
import Protocolo.Interlocutor;
import Protocolo.Message;
import Protocolo.MessageType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

public class LoginThread implements Runnable{
    Socket socket = null;
    final String softwareID = "HundirLaFlota Server V 0.1";
    final String clientID = "HundirLaFlota Cliente V 0.1";
    Message msg = null;
    InputStream streamInput = null;
    OutputStream streamOutput = null;
    BufferedReader br = null;
    String driver = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://192.168.1.150/HundirLaFlota";
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet rs;
    String buffer = null;
    String passdb = null;

    
    public LoginThread(Socket socket, Message message) throws SQLException, SocketException{
        this.socket = socket;
        this.msg = message;
        socket.setSoTimeout(3000);
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, "root", "alumno");
        } catch (ClassNotFoundException cnfe) {
            System.out.printf("Not found the jdbc driver %s\n", driver);
        }
    }
    
    @Override
    public void run () {
        try {
            streamInput = socket.getInputStream();
            streamOutput = socket.getOutputStream();
        } catch (IOException ex) {
            Logger.getLogger(LoginThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        br = new BufferedReader(new InputStreamReader(streamInput));
        buffer = null;
        
        System.out.println(msg.toString());
        if(msg.getInterlocutor().getData().getContent().equals(clientID)){
            Message.sendXML( new Message( MessageType.LOGIN, new Interlocutor( "server", new Data( softwareID ) ) ), streamOutput );
            try {
                while(!socket.isClosed()&&buffer==null){
                    try{
                        buffer = br.readLine();
                    }catch(SocketTimeoutException e){
                        buffer = null;
                    }
                }
                if(socket.isClosed()){
                    return;
                }
            } catch (IOException ex) {
                Logger.getLogger(Dispatcher.class.getName()).log(Level.SEVERE, null, ex);
            }
            msg = null;
            try {
                msg = Message.fromXML(buffer);
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(Dispatcher.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(Dispatcher.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Dispatcher.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TransformerException ex) {
                Logger.getLogger(Dispatcher.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(msg.toString());
            try {
                preparedStatement = connection.prepareStatement("SELECT Jugador.usuario, Jugador.password FROM Jugador WHERE Jugador.usuario = ?");
                preparedStatement.setString(1, msg.getInterlocutor().getData().getUser());
                rs = preparedStatement.executeQuery();
                if(!rs.next()){
                    Message.sendXML( new Message( MessageType.LOGIN, new Interlocutor( "server", new Data( "credenciales_incorrectas" ) ) ), streamOutput );
                }else{
                    passdb=rs.getString(2);
                    if(msg.getInterlocutor().getData().getPassword().equals(passdb)){
                        Message.sendXML( new Message( MessageType.LOGIN, new Interlocutor( "server", new Data( "Sesion_Id" ) ) ), streamOutput );
                    }else{
                        Message.sendXML( new Message( MessageType.LOGIN, new Interlocutor( "server", new Data( "credenciales_incorrectas" ) ) ), streamOutput );
                    }
                }
                socket.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoginThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(LoginThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            try {
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(LoginThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
