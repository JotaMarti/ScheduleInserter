package MODEL;

import com.jcabi.xml.XMLDocument;
import java.io.File;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;


public class ParseInput {

    private String textArea;
    private static DOMSource source;
    private DocumentBuilderFactory docFactory;
    private DocumentBuilder docBuilder;
    private Document document;

    public ParseInput(String textArea) {
        this.textArea = textArea;
    }

    public Document getDocument() {
        return document;
    }

    public void click() {
        /*Path path = Paths.get("C:\\Dropbox\\proyectoXML\\prueba.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write("Hello World !!");
        }*/
        try {

            //File file = new File("C:\\\\Dropbox\\\\proyectoXML\\\\xml.xml");
            //Leer documento
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();
            System.out.println(textArea);
            Document doc = dBuilder.parse(new InputSource(new StringReader(textArea)));
            //String xml = new XMLDocument(doc).toString();
            //System.out.println(xml);
            //Escribir documento
            //docFactory = DocumentBuilderFactory.newInstance();
            //docBuilder = docFactory.newDocumentBuilder();

            //document = docBuilder.newDocument();
            String root = doc.getDocumentElement().getNodeName();
            Element rootElement = document.createElement(root);
            //document.appendChild(rootElement);

            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            if (doc.hasChildNodes()) {
                printNote(doc.getChildNodes(), doc, rootElement);

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printNote(NodeList nodeList, Document document, Element rootElement) {
        //Element rootElement = document.createElement(root);
        //document.appendChild(rootElement);
        boolean isFinished = false;
        for (int count = 0; count < nodeList.getLength(); count++) {

            Node tempNode = nodeList.item(count);

            // make sure it's element node.
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

                // get node name and value
                //System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
                Element staff = document.createElement(tempNode.getNodeName());
                if (tempNode.getNodeName().equals("UDConfig")) {
                    document.appendChild(staff);
                } else {
                    rootElement.appendChild(staff);
                }

                /*if (tempNode.getNodeName().equals("Scenes")) {
                    document.appendChild(staff);
                } else {
                    rootElement.appendChild(staff);
                }*/
                //System.out.println("Node Value =" + tempNode.getTextContent());
                if (tempNode.hasAttributes()) {

                    // get attributes names and values
                    NamedNodeMap nodeMap = tempNode.getAttributes();

                    for (int i = 0; i < nodeMap.getLength(); i++) {
                        /*Node node = nodeMap.item(i);
                        if (node.getNodeName().equals("IP")) {
                            
                            Attr attr = document.createAttribute(node.getNodeName());
                            attr.setValue("10.10.10.10");
                            staff.setAttributeNode(attr);
                        } else {
                            
                            Attr attr = document.createAttribute(node.getNodeName());
                            attr.setValue(node.getNodeValue());
                            staff.setAttributeNode(attr);
                        }*/
                        Node node = nodeMap.item(i);
                        Attr attr = document.createAttribute(node.getNodeName());
                        attr.setValue(node.getNodeValue());
                        staff.setAttributeNode(attr);
                        //System.out.println("attr name : " + node.getNodeName());
                        //System.out.println("attr value : " + node.getNodeValue());
                    }

                }

                if (tempNode.hasChildNodes()) {

                    // loop again if has child nodes
                    printNote(tempNode.getChildNodes(), document, staff);

                }

                if (tempNode.getNodeName().equals("UDConfig")) {
                    isFinished = true;
                }

                //System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");
            }

        }
        /*if (isFinished) {
            try {
                //TransformerFactory transformerFactory = TransformerFactory.newInstance();
                //Transformer transformer = transformerFactory.newTransformer();
                source = new DOMSource(document);
                //StreamResult result = new StreamResult(new File("C:\\Dropbox\\proyectoXML\\test.xml"));
                //transformer.transform(source, result);

                System.out.println("File saved!");

            } catch (Exception e) {
            }
        }*/

    }

    public static Document loadXMLFromString(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }

}
