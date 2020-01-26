/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

public class ReadAndWrite {

    private static String myInput;

    public static void click(Document documentText, String input, File file) {
        /*Path path = Paths.get("C:\\Dropbox\\proyectoXML\\prueba.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write("Hello World !!");
        }*/
        try {
            myInput = input;
            //Leer documento
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();

            Document doc = dBuilder.parse(file);
            Document documentTextArea = documentText;
            //Escribir documento
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document document = docBuilder.newDocument();
            String root = doc.getDocumentElement().getNodeName();
            Element rootElement = document.createElement(root);
            //document.appendChild(rootElement);

            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            if (doc.hasChildNodes()) {
                printNote(doc.getChildNodes(), document, rootElement, documentTextArea, myInput, file);

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printNote(NodeList nodeList, Document document, Element rootElement, Document documentTextArea, String input, File file) {
        //Element rootElement = document.createElement(root);
        //document.appendChild(rootElement);
        boolean isFinished = false;
        for (int count = 0; count < nodeList.getLength(); count++) {
            Node tempNode = nodeList.item(count);

            // make sure it's element node.
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

                // get node name and value
                System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
                Element staff = document.createElement(tempNode.getNodeName());
                if (tempNode.getNodeName().equals("UDConfig")) {
                    document.appendChild(staff);
                } else if (tempNode.getNodeName().equals(input)) {
                    NodeList myNode1 = documentTextArea.getChildNodes();

                    for (int count1 = 0; count1 < myNode1.getLength(); count1++) {

                        Node tempNode1 = myNode1.item(count1);
                        if (tempNode1.getNodeType() == Node.ELEMENT_NODE) {
                            Element staff1 = document.createElement(tempNode1.getNodeName());
                            rootElement.appendChild(staff1);
                            if (tempNode1.hasAttributes()) {

                                // get attributes names and values
                                NamedNodeMap nodeMap1 = tempNode1.getAttributes();

                                for (int j = 0; j < nodeMap1.getLength(); j++) {
                                    Node node1 = nodeMap1.item(j);
                                    Attr attr1 = document.createAttribute(node1.getNodeName());
                                    attr1.setValue(node1.getNodeValue());
                                    staff1.setAttributeNode(attr1);
                                    System.out.println("attr name : " + node1.getNodeName());
                                    System.out.println("attr value : " + node1.getNodeValue());
                                }

                            }

                            if (tempNode1.hasChildNodes()) {

                                // loop again if has child nodes
                                printNote1(tempNode1.getChildNodes(), document, staff1, documentTextArea);

                            }
                        }

                    }
                } else {
                    rootElement.appendChild(staff);
                }

                //if () {
                //document.appendChild(staff);
                //}
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
                    printNote(tempNode.getChildNodes(), document, staff, documentTextArea, input, file);

                }

                if (tempNode.getNodeName().equals("UDConfig")) {
                    isFinished = true;
                }

                System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");
            }

        }
        if (isFinished) {
            try {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(document);
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
                String fecha;
                fecha = dateFormat.format(date);
                String year = fecha.substring(0, 4);
                String mes = fecha.substring(5, 7);
                String dia = fecha.substring(8, 10);
                String hora = fecha.substring(11, 13);
                String minuto = fecha.substring(14, 16);
                String segundo = fecha.substring(17, 19);
                System.out.println(year.length());
                System.out.println(mes.length());
                System.out.println(dia.length());
                System.out.println(hora.length());
                System.out.println(minuto.length());
                System.out.println(segundo.length());
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

    private static void printNote1(NodeList nodeList2, Document document, Element rootElement, Document documentTextArea) {

        for (int count3 = 0; count3 < nodeList2.getLength(); count3++) {

            Node tempNode2 = nodeList2.item(count3);

            // make sure it's element node.
            if (tempNode2.getNodeType() == Node.ELEMENT_NODE) {

                // get node name and value
                //System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
                Element staff2 = document.createElement(tempNode2.getNodeName());
                if (tempNode2.getNodeName().equals("UDConfig")) {
                    document.appendChild(staff2);
                } else {
                    rootElement.appendChild(staff2);
                }

                //System.out.println("Node Value =" + tempNode.getTextContent());
                if (tempNode2.hasAttributes()) {

                    // get attributes names and values
                    NamedNodeMap nodeMap2 = tempNode2.getAttributes();

                    for (int k = 0; k < nodeMap2.getLength(); k++) {
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
                        Node node2 = nodeMap2.item(k);
                        Attr attr2 = document.createAttribute(node2.getNodeName());
                        attr2.setValue(node2.getNodeValue());
                        staff2.setAttributeNode(attr2);
                        System.out.println("attr name : " + node2.getNodeName());
                        System.out.println("attr value : " + node2.getNodeValue());
                    }

                }

                if (tempNode2.hasChildNodes()) {

                    // loop again if has child nodes
                    printNote1(tempNode2.getChildNodes(), document, staff2, documentTextArea);

                }

                //System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");
            }

        }

    }

}
