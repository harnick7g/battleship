/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XML;

import Protocolo.Message;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Pedro Miguel Navas Campoy
 */
public class XML {
    private Element rootElement;
    private Document document;

    public XML ( String strRootElement ) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch ( ParserConfigurationException ex ) {
            Logger.getLogger( Message.class.getName() ).log( Level.SEVERE, null, ex );
        }
        document = builder.getDOMImplementation().createDocument( null, strRootElement, null );
        document.setXmlVersion( "1.0" );
        rootElement = document.getDocumentElement();
    }

    public Element getRootElement () {
        return rootElement;
    }

    public XML () {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch ( ParserConfigurationException ex ) {
            Logger.getLogger( Message.class.getName() ).log( Level.SEVERE, null, ex );
        }
        document = builder.getDOMImplementation().createDocument( null, null, null );
        document.setXmlVersion( "1.0" );
    }

    public Document getDocument () {
        return document;
    }

    @Override
    public String toString () {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = null;
        StreamResult result = null;

        try {
            transformer = factory.newTransformer();
        } catch ( TransformerConfigurationException ex ) {
            Logger.getLogger( XML.class.getName() ).log( Level.SEVERE, null, ex );
        } finally {
            result = new StreamResult( outputStream );
        }
        try {
            Source source = new DOMSource( document );
            transformer.transform( source, result );
        } catch ( TransformerException ex ) {
            Logger.getLogger( XML.class.getName() ).log( Level.SEVERE, null, ex );
        }

        String strResult = new String(outputStream.toByteArray());
        return strResult;
    }
    
    static public XML fromString( String strXML ) throws ParserConfigurationException, SAXException, IOException, TransformerException{
        XML newXML = new XML();
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(strXML));
        Document document = builder.parse(is);
        
        newXML.document = document;
        newXML.rootElement = document.getDocumentElement();
        
        return newXML;
    }
}
