package MODEL;

import javax.xml.transform.dom.DOMSource;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class ParseInputTest {

    private String textArea;
    private static DOMSource source;
    private DocumentBuilderFactory docFactory;
    private DocumentBuilder docBuilder;
    private Document document;

    public ParseInputTest(String textArea) {
        this.textArea = textArea;
        
        document = convertStringToXMLDocument(this.textArea);
    }

    private static Document convertStringToXMLDocument(String xmlString) {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();

            //Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
        public Document getDocument() {
        return document;
    }

}
