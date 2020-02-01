package MODEL;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
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

public class CopyScene {

    private static String firstSceneInput;
    private static String secondSceneInput;

    public static void click(String firstScene, String secondScene, File file) {
        
        firstSceneInput = firstScene;
        secondSceneInput = secondScene;

        try {
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document documentNew = docBuilder.newDocument();
            Document documentFile = dBuilder.parse(file);

            String root = documentFile.getDocumentElement().getNodeName();
            Element rootElement = documentNew.createElement(root);
            //document.appendChild(rootElement);

            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            if (documentFile.hasChildNodes()) {
                printNote(documentFile.getChildNodes(), documentNew, rootElement, file);

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printNote(NodeList nodeList, Document documentNew, Element rootElement, File file) {

        boolean isFinished = false;
        for (int count = 0; count < nodeList.getLength(); count++) {
            Node tempNode = nodeList.item(count);

            // make sure it's element node.
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

                // get node name and value
                //System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
                Element element = documentNew.createElement(tempNode.getNodeName());
                //System.out.println(tempNode.getNodeName());
                if (tempNode.getNodeName().equals("UDConfig")) {
                    documentNew.appendChild(element);
                } else if (tempNode.getNodeName().equals("Scene")) {
                    printNoteInput(documentNew, rootElement, nodeList, firstSceneInput);
                    printNoteInput(documentNew, rootElement, nodeList, secondSceneInput);
                } else {
                    rootElement.appendChild(element);
                }

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
                        Attr attr = documentNew.createAttribute(node.getNodeName());
                        attr.setValue(node.getNodeValue());
                        element.setAttributeNode(attr);
                        //System.out.println("attr name : " + node.getNodeName());
                        //System.out.println("attr value : " + node.getNodeValue());
                    }

                }

                if (tempNode.hasChildNodes()) {

                    // loop again if has child nodes
                    printNote(tempNode.getChildNodes(), documentNew, element, file);

                }

                if (tempNode.getNodeName().equals("UDConfig")) {
                    isFinished = true;
                }

                //System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");
            }

        }
        if (isFinished) {
            try {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(documentNew);
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                //System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
                String fecha = dateFormat.format(date);
                String year = fecha.substring(0, 4);
                String mes = fecha.substring(5, 7);
                String dia = fecha.substring(8, 10);
                String hora = fecha.substring(11, 13);
                String minuto = fecha.substring(14, 16);
                String segundo = fecha.substring(17, 19);

                StreamResult result = new StreamResult(file.getParent() + "\\" + year + "-" + mes + "-" + dia + " " + hora + "·" + minuto + "·" + segundo + " (00).udx");
                //System.out.println(file.getParent() + "\\test.xml");
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                transformer.transform(source, result);

                System.out.println("File saved!");

            } catch (Exception e) {
            }
        }

    }

    private static void printNoteInput(Document documentNew, Element rootElement, NodeList myNodeInput, String sceneName) {
        for (int i = 0; i < myNodeInput.getLength(); i++) {

            Node tempNodeInput = myNodeInput.item(i);
            if (tempNodeInput.getNodeType() == Node.ELEMENT_NODE) {
                Element elementTextArea = documentNew.createElement(tempNodeInput.getNodeName());
                rootElement.appendChild(elementTextArea);
                if (tempNodeInput.hasAttributes()) {

                    // get attributes names and values
                    NamedNodeMap nodeMapInput = tempNodeInput.getAttributes();

                    for (int j = 0; j < nodeMapInput.getLength(); j++) {

                        Node node = nodeMapInput.item(j);
                        //System.out.println(nodeMapInput.item(j));
                        //System.out.println(tempNodeInput.getNodeName());
                        //System.out.println(rootElement.getTagName().equals("Scene") + " SCENE");
                        if (node.getNodeName().equals("Name") && tempNodeInput.getNodeName().equals("Scene")) {
                            Attr attr = documentNew.createAttribute(node.getNodeName());
                            attr.setValue(sceneName);
                            elementTextArea.setAttributeNode(attr);
                        } else {
                            Attr attr = documentNew.createAttribute(node.getNodeName());
                            attr.setValue(node.getNodeValue());
                            elementTextArea.setAttributeNode(attr);
                        }

                        //System.out.println("attr name : " + nodeInput.getNodeName());
                        //System.out.println("attr value : " + nodeInput.getNodeValue());
                    }

                }

                if (tempNodeInput.hasChildNodes()) {

                    // loop again if has child nodes
                    printNoteInput(documentNew, elementTextArea, tempNodeInput.getChildNodes(), sceneName);
                }
            }

        }
    }


}
