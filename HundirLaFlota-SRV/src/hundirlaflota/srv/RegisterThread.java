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

/**
 *
 * @author harnick
 */
public class RegisterThread implements Runnable{
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
    String sqlUser = null;
    String sqlNick = null;
    String sqlEmail = null;
    int idJug;
    
    public RegisterThread(Socket socket, Message message) throws SocketException, SQLException{
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
            Message.sendXML( new Message( MessageType.REGISTER, new Interlocutor( "server", new Data( softwareID ) ) ), streamOutput );
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
                preparedStatement = connection.prepareStatement("SELECT Jugador.idJugador FROM Jugador WHERE Jugador.usuario = ?");
                preparedStatement.setString(1, msg.getInterlocutor().getData().getUser());
                rs = preparedStatement.executeQuery();
                if(!rs.next()){
                    sqlUser = "ok";
                }else{
                    sqlUser = "x";
                }
                preparedStatement = connection.prepareStatement("SELECT Jugador.idJugador FROM Jugador WHERE Jugador.nick = ?");
                preparedStatement.setString(1, msg.getInterlocutor().getData().getNick());
                rs = preparedStatement.executeQuery();
                if(!rs.next()){
                    sqlNick = "ok";
                }else{
                    sqlNick = "x";
                }
                preparedStatement = connection.prepareStatement("SELECT Jugador.idJugador FROM Jugador WHERE Jugador.email = ?");
                preparedStatement.setString(1, msg.getInterlocutor().getData().getEmail());
                rs = preparedStatement.executeQuery();
                if(!rs.next()){
                    sqlEmail = "ok";
                }else{
                    sqlEmail = "x";
                }
                if(sqlEmail.equals("ok")&&sqlUser.equals("ok")&&sqlNick.equals("ok")){
                    preparedStatement = connection.prepareStatement("SELECT Jugador.idJugador FROM Jugador ORDER BY Jugador.idJugador DESC LIMIT 1");
                    rs = preparedStatement.executeQuery();
                    if (rs.next()){
                        idJug = (int)rs.getInt(1);
                    }
                    preparedStatement = connection.prepareStatement("INSERT INTO Jugador VALUES(?,?,?,?,0,?)");
                    preparedStatement.setInt(1, idJug+1);
                    preparedStatement.setString(2, msg.getInterlocutor().getData().getNick());
                    preparedStatement.setString(3, msg.getInterlocutor().getData().getUser());
                    preparedStatement.setString(4, msg.getInterlocutor().getData().getPassword());
                    preparedStatement.setString(5, msg.getInterlocutor().getData().getEmail());
                    preparedStatement.executeUpdate();
                    Message.sendXML( new Message( MessageType.REGISTER, new Interlocutor( "server", new Data( "ok" ) ) ), streamOutput );
                }else{
                    Message.sendXML( new Message( MessageType.REGISTER, new Interlocutor( "server", new Data( sqlUser, null, sqlNick, sqlEmail ) ) ), streamOutput );
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
