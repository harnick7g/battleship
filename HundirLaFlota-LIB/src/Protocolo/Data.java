/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocolo;

import XML.XML;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author Pedro Miguel Navas Campoy
 */
public class Data extends BaseMessage {
    private String user;
    private String nick;
    private String password;
    private String email;
    private String content;

    public Data ( String user, String password ) {
        this.user = user;
        this.password = password;
    }
    
    public Data ( String user, String password, String nick, String email ) {
        this.user = user;
        this.nick = nick;
        this.password = password;
        this.email = email;
    }
    
    public Data ( Node node ) {
        if(node.getAttributes().getNamedItem("user")!=null){
            this.user = node.getAttributes().
                getNamedItem("user").getNodeValue();
        }
        if(node.getAttributes().getNamedItem("nick")!=null){
            this.nick = node.getAttributes().
                getNamedItem("nick").getNodeValue();
        }
        if(node.getAttributes().getNamedItem("password")!=null){
            this.password = node.getAttributes().
                getNamedItem("password").getNodeValue();
        }
        if(node.getAttributes().getNamedItem("email")!=null){
            this.email = node.getAttributes().
                getNamedItem("email").getNodeValue();
        }
        this.content = node.getTextContent();
        
    }
    
    public Data ( String content ) {
        this.user = null;
        this.nick = null;
        this.password = null;
        this.email = null;
        this.content = content;
    }

    public String getUser () {
        return user;
    }

    public void setUser ( String user ) {
        this.user = user;
    }
    
    public String getNick () {
        return nick;
    }

    public void setNick ( String nick ) {
        this.nick = nick;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword ( String password ) {
        this.password = password;
    }
    
    public String getEmail () {
        return email;
    }

    public void setEmail ( String email ) {
        this.email = email;
    }

    public String getContent () {
        return content == null ? "" : content;
    }

    public void setContent ( String content ) {
        this.content = content;
    }

    @Override
    public Element toXML ( Node parent ) {
        Element element = ( Element ) parent.getOwnerDocument().createElement( getClass().getSimpleName() );
        element.setTextContent( getContent() );
        if(user!=null){
            element.setAttribute( "user", getUser() );
        }
        if(nick!=null){
            element.setAttribute( "nick", getNick() );
        }
        if(password!=null){
            element.setAttribute( "password", getPassword() );
        }
        if(email!=null){
            element.setAttribute( "email", getEmail() );
        }

        return element;
    }

    @Override
    public String toString () {
        String str;

        str = "<" + getClass().getSimpleName() + " user=\"" + getUser() + "\" nick=\"" + getNick() + "\"" + " password=\"" + getPassword() + "\" email=\"" + getEmail() + "\">";
        str += getContent();
        str += "</" + getClass().getSimpleName() + ">";
        return str;
    }
}
