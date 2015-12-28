package Protocolo;

import XML.XML;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author Pedro Miguel Navas Campoy
 */
public abstract class BaseMessage {

    /**
     * Simplifica el uso de <code>this.getClass().getSimpleName()</code>
     *
     * @return Un String con el nombre simple de la clase.
     */
    public final String getClassName () {
        return getClass().getSimpleName();
    }

    /**
     * Enviar un mensaje...
     *
     * @param msg Mensaje a enviar.
     * @param out Por donde se envía.
     */
    public static void sendXML ( Message msg, OutputStream out ) {
        XML xml = new XML();
        xml.getDocument().appendChild( msg.toXML( xml.getDocument() ) );
        //msg.toXML( ( Element ) xml.getDocument() );
        String strXML = xml.toString();
        try {
            out.write( (strXML+"\n").getBytes() );
            //out.write( "\n".getBytes() );
            out.flush();
        } catch ( IOException ex ) {
            Logger.getLogger( Message.class.getName() ).log( Level.SEVERE, null, ex );
        }

    }

    /**
     * Método que retorna un nodo xml que representa al objeto actual para
     * insertarlo en <b>{@link #parent parent}</b>.
     *
     *
     * @param parent Rama xml ({@link org.w3c.dom.Node Node}), donde insertar el
     *               nodo creado.
     *
     * @return el nodo creado.
     */
    public abstract Element toXML ( Node parent );
}
