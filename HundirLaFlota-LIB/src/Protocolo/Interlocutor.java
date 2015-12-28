package Protocolo;

import XML.XML;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author Pedro Miguel Navas Campoy
 */
public class Interlocutor extends BaseMessage {
    private String name;
    private Data data;

    public Interlocutor ( String name, Data data ) {
        this.name = name;
        this.data = data;
    }
    
    public Interlocutor ( Node node ) {
        this.name = node.getAttributes().
                getNamedItem("name").getNodeValue();
        this.data = new Data( node.getFirstChild());
    }

    public String getName () {
        return name;
    }

    public void setName ( String name ) {
        this.name = name;
    }

    public Data getData () {
        return data;
    }

    public void setData ( Data data ) {
        this.data = data;
    }

    @Override
    public Element toXML ( Node parent ) {
        Element element = ( Element ) parent.getOwnerDocument().createElement( getClass().getSimpleName() );
        element.setAttribute( "name", getName() );

        element.appendChild( getData().toXML( element ) );

        return element;
    }

    @Override
    public String toString () {
        String str;

        str = "<" + getClass().getSimpleName() + " name=\"" + getName() + "\">";
        str += getData().toString();
        str += "</" + getClass().getSimpleName() + ">";
        return str;
    }

}
