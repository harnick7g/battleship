/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocolo;

import XML.XML;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * Literarura barata sobre el uso u utilidad de esta clase
 *
 * @author Pedro Miguel Navas Campoy
 */
public class Message extends BaseMessage {
    private MessageType type;
    private Interlocutor interlocutor;

    /**
     * Este es el constructor del mensaje....
     *
     * @param type         Enumeración con el tipo del mensaje
     * @param interlocutor Objeto con e
     */
    public Message ( MessageType type, Interlocutor interlocutor ) {
        this.type = type;
        this.interlocutor = interlocutor;
    }
    /**
     * constructor a partir de un elemento XML
     * 
     * @param node Elemento XML
     */
    private Message( Node node){
        int intType = Integer.parseInt( node.getAttributes().
                getNamedItem("type").getNodeValue());
        this.type = MessageType.fromOrdinal(intType);
        this.interlocutor = new Interlocutor( node.getFirstChild());
    }

    public String getStringType () {
        return String.format( "%02d", type.ordinal() );
    }
    
    public MessageType getType(){
        return type;
    }

    public Interlocutor getInterlocutor () {
        return interlocutor;
    }

    /**
     * Enviar un mensaje...
     *
     * @param msg Mensaje a enviar
     * @param out Por donde se envía
     */
    //public static void sendXML ( Message msg, OutputStream out ) {
    /*
     * XML xml = new XML( "message" ); xml.getRootElement().setAttribute(
     * "type", msg.getType() ); Element xmlInterlocutor = ( Element )
     * xml.createElement( xml.getRootElement(), "interlocutor" );
     * xmlInterlocutor.setAttribute( "name", msg.getInterlocutor().getName() );
     * Element xmlData = ( Element ) xml.createElement( xmlInterlocutor, "data",
     * msg.getInterlocutor().getData().getContent() ); if (
     * !msg.getInterlocutor().getData().getUser().isEmpty() ) {
     * xmlData.setAttribute( "user", msg.getInterlocutor().getData().getUser()
     * ); } if ( !msg.getInterlocutor().getData().getPassword().isEmpty() ) {
     * xmlData.setAttribute( "password",
     * msg.getInterlocutor().getData().getPassword() ); }
     *
     * byte[] result = xml.result(); try { out.write( result ); } catch (
     * IOException ex ) { Logger.getLogger( Message.class.getName() ).log(
     * Level.SEVERE, null, ex ); }
     */
    //    XML xml = new XML();
    //    Document doc = ( Document ) xml.getDocument();
    //    xml.getDocument().appendChild( msg.toXML( doc ) );
    //msg.toXML( ( Element ) xml.getDocument() );
    //    byte[] result = xml.result();
    //    try {
    //        out.write( result );
    //    } catch ( IOException ex ) {
    //        Logger.getLogger( Message.class.getName() ).log( Level.SEVERE, null, ex );
    //    }
    // }
    /**
     *
     * @param parent
     *
     * @return
     */
    @Override
    public Element toXML ( Node parent ) {
        Element element = ( ( Document ) parent ).createElement( getClass().getSimpleName() );
        element.setAttribute("type", getStringType() );

        element.appendChild( getInterlocutor().toXML( element ) );

        //return ( Element ) parent.appendChild( element );
        return element;
    }

    @Override
    public String toString () {
        String str;

        str = "<" + getClass().getSimpleName() + " type=\"" + getStringType() + "\">";
        str += getInterlocutor().toString();
        str += "</" + getClass().getSimpleName() + ">";
        return str;
    }

    /**
     * Obtiene un mensaje a partir de una cadena XML
     *
     * @param strXML Cadena XML
     *
     * @return El objeto Message generado a partir del XML de la cadena.
     */
    static public Message fromXML (String strXML) throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException{
        XML xml = XML.fromString( strXML );
        
        /*TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(xml.getDocument()), new StreamResult(writer));
        String output = writer.getBuffer().toString().replaceAll("\n|\r", "");
        System.out.println("PRINTAMEEEEE Message.fromXML");
        System.out.println(output);*/
        
        Message newMessage = new Message( xml.getRootElement() );
        
        return newMessage;
    }
}