/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hundirlaflota;

import Protocolo.Data;
import Protocolo.Interlocutor;
import Protocolo.Message;
import Protocolo.MessageType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;
/**
 *
 * @author Pedro Miguel Navas Campoy
 */
public class HundirLaFlota {
    Socket clientSocket;
    final int remotePort;
    final String remoteHost;
    private String sesionID = null;
    final String softwareID = "HundirLaFlota Cliente V 0.1";
    final String serverID = "HundirLaFlota Server V 0.1";
    Message mes = null;
    String buffer = null;
    BufferedReader br = null;

    /**
     * @param args the command line arguments
     */
    public HundirLaFlota () {
        remotePort = 42320;
        remoteHost = "localhost";
    }

    public boolean execLogin(String user, String pass) throws IOException{
        mes = new Message( MessageType.LOGIN, new Interlocutor(
            "client", new Data( softwareID ) ) );
        System.out.println(mes.toString());
        try {
            clientSocket = new Socket( remoteHost, remotePort );
            clientSocket.setSoTimeout(3000);
            br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            Message.sendXML( mes, clientSocket.getOutputStream() );
        } catch ( IOException ex ) {
            Logger.getLogger( HundirLaFlota.class.getName() ).log( Level.SEVERE, null, ex );
            Logger.getLogger( HundirLaFlota.class.getName() ).log( Level.SEVERE, "Error al realizar la conexión con servidor" );
            System.exit( 1 );
        }
        
        try {
            while(!clientSocket.isClosed()&&buffer==null){
                try{
                    buffer = br.readLine();
                }catch(SocketTimeoutException e){
                    buffer = null;
                }
            }
            if(clientSocket.isClosed()){
                return false;
            }
        } catch (IOException ex) {
            Logger.getLogger(HundirLaFlota.class.getName()).log(Level.SEVERE, null, ex);
        }

        mes = null;
        
        try {
            mes = Message.fromXML(buffer);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(HundirLaFlota.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(HundirLaFlota.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HundirLaFlota.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(HundirLaFlota.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(mes.toString());
        buffer = null;
        if(mes.getInterlocutor().getData().getContent().equals(serverID)){
            Message.sendXML( new Message( MessageType.LOGIN, new Interlocutor( "client", new Data( user, pass ) ) ), clientSocket.getOutputStream() );
            try {
                while(!clientSocket.isClosed()&&buffer==null){
                    try{
                        buffer = br.readLine();
                    }catch(SocketTimeoutException e){
                        buffer = null;
                    }
                }
                if(clientSocket.isClosed()&&buffer==null){
                    return false;
                }
            } catch (IOException ex) {
                Logger.getLogger(HundirLaFlota.class.getName()).log(Level.SEVERE, null, ex);
            }

            mes = null;

            try {
                mes = Message.fromXML(buffer);
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(HundirLaFlota.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(HundirLaFlota.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(HundirLaFlota.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TransformerException ex) {
                Logger.getLogger(HundirLaFlota.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(mes.toString());
            if(mes.getInterlocutor().getData().getContent().equals("credenciales_incorrectas")){
                if(!clientSocket.isClosed()){
                    clientSocket.close();
                }
                return false;
            }else{
                sesionID = mes.getInterlocutor().getData().getContent();
                //Guardar sesionID
                clientSocket.close();
                return true;
            }
        }else{
            clientSocket.close();
            return false;
        }
    }
    
    public String execRegister(String user, String pass, String nick, String email) throws IOException{
        mes = new Message( MessageType.REGISTER, new Interlocutor(
            "client", new Data( softwareID ) ) );
        try {
            System.out.println(mes.toString());
            clientSocket = new Socket( remoteHost, remotePort );
            clientSocket.setSoTimeout(3000);
            br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            Message.sendXML( mes, clientSocket.getOutputStream() );
        } catch ( IOException ex ) {
            Logger.getLogger( HundirLaFlota.class.getName() ).log( Level.SEVERE, null, ex );
            Logger.getLogger( HundirLaFlota.class.getName() ).log( Level.SEVERE, "Error al realizar la conexión con servidor" );
            System.exit( 1 );
        }
        
        try {
            while(!clientSocket.isClosed()&&buffer==null){
                try{
                    buffer = br.readLine();
                }catch(SocketTimeoutException e){
                    buffer = null;
                }
            }
            if(clientSocket.isClosed()){
                return "close";
            }
        } catch (IOException ex) {
            Logger.getLogger(HundirLaFlota.class.getName()).log(Level.SEVERE, null, ex);
        }

        mes = null;
        
        try {
            mes = Message.fromXML(buffer);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(HundirLaFlota.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(HundirLaFlota.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HundirLaFlota.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(HundirLaFlota.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(mes.toString());
        buffer = null;
        if(mes.getInterlocutor().getData().getContent().equals(serverID)){
            Message.sendXML( new Message( MessageType.REGISTER, new Interlocutor( "client", new Data( user, pass, nick,  email) ) ), clientSocket.getOutputStream() );
            try {
                while(!clientSocket.isClosed()&&buffer==null){
                    try{
                        buffer = br.readLine();
                    }catch(SocketTimeoutException e){
                        buffer = null;
                    }
                }
                if(clientSocket.isClosed()&&buffer==null){
                    return "close";
                }
            } catch (IOException ex) {
                Logger.getLogger(HundirLaFlota.class.getName()).log(Level.SEVERE, null, ex);
            }

            mes = null;

            try {
                mes = Message.fromXML(buffer);
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(HundirLaFlota.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(HundirLaFlota.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(HundirLaFlota.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TransformerException ex) {
                Logger.getLogger(HundirLaFlota.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(mes.toString());
            if(mes.getInterlocutor().getData().getContent().equals("ok")){
                if(!clientSocket.isClosed()){
                    clientSocket.close();
                }
                return "ok";
            }else{
                if(!clientSocket.isClosed()){
                    clientSocket.close();
                }
                String alertar = "";
                if(mes.getInterlocutor().getData().getUser().equals("x")){
                    alertar+="user ";
                }
                if(mes.getInterlocutor().getData().getNick().equals("x")){
                    alertar+="nick ";
                }
                if(mes.getInterlocutor().getData().getEmail().equals("x")){
                    alertar+="email ";
                }
                System.out.println("lo que recibe login.java "+alertar);
                return alertar;
            }
        }else{
            clientSocket.close();
            return "close";
        }
    }
}
