package CONTROLLER;

import MODEL.ParseInput;
import MODEL.ParseInputTest;
import MODEL.ReadAndWrite;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.control.TextField;
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

public class MainViewController implements Initializable {

    @FXML
    private TextArea textArea;
    @FXML
    private Button btnSet;
    @FXML
    private TextField txtField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void clickSet(ActionEvent event) {
        String dniR = "Unidad-\\d*";
        int contador = 0;
        ParseInputTest input = new ParseInputTest(textArea.getText());

        //ReadAndWrite.click(input.getDocument(), txtField.getText());
        File folders = new File("C:\\Ngaro\\Runtimes\\");
        String[] directories = folders.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });

        for (int i = 0; i < directories.length; i++) {
            //System.out.println(directories[i]);
            if (directories[i].matches(dniR)) {
                contador++;
            }

        }
        
        //File last = getLatestFilefromDir("C:\\Ngaro\\Runtimes\\Unidad-0\\backup");
        
        
        
        for (int i = 0; i < contador; i++) {
            File last = getLatestFilefromDir("C:\\Ngaro\\Runtimes\\Unidad-" + i + "\\backup");
            ReadAndWrite.click(input.getDocument(), txtField.getText(), last);
            
        }
        
        
        
        
        
        
        
        
        
        
        /*List<String[]> allFiles = new ArrayList<String[]>();
        
        for (int i = 0; i < contador; i++) {
            File file = new File("C:\\Ngaro\\Runtimes\\Unidad-" + i + "\\backup");
            String[] files = file.list(new FilenameFilter() {
                @Override
                public boolean accept(File current, String name) {
                    //return new File(current, name).isDirectory();
                    return new File(current, name).isFile();
                }   
            });
            allFiles.add(files);
        }*/

        //System.out.println(Arrays.toString(directories));
        /*Path path = Paths.get("C:\\Dropbox\\proyectoXML\\prueba.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write("Hello World !!");
        }*/
 /*try {

            File file = new File("C:\\\\Dropbox\\\\proyectoXML\\\\xml.xml");
            //Leer documento
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();

            Document doc = dBuilder.parse(file);

            //Escribir documento
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document document = docBuilder.newDocument();
            String root = doc.getDocumentElement().getNodeName();
            Element rootElement = document.createElement(root);
            //document.appendChild(rootElement);

            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            if (doc.hasChildNodes()) {
                printNote(doc.getChildNodes(), document, rootElement);

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }*/

 /*private static void printNote(NodeList nodeList, Document document, Element rootElement) {
        System.out.println("test");
        //Element rootElement = document.createElement(root);
        //document.appendChild(rootElement);

        for (int count = 0; count < nodeList.getLength(); count++) {

            Node tempNode = nodeList.item(count);

            // make sure it's element node.
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

                // get node name and value
                System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
                Element staff = document.createElement(tempNode.getNodeName());
                if (tempNode.getNodeName().equals("UDConfig")) {
                    document.appendChild(staff);
                } else {
                    rootElement.appendChild(staff);
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
 /*Node node = nodeMap.item(i);
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

                //System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");
            }

        }
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("C:\\Dropbox\\proyectoXML\\test.xml"));
            transformer.transform(source, result);

            System.out.println("File saved!");
        } catch (Exception e) {
        }

    }*/
    }
    
    private File getLatestFilefromDir(String dirPath){
    File dir = new File(dirPath);
    File[] files = dir.listFiles();
    if (files == null || files.length == 0) {
        return null;
    }

    File lastModifiedFile = files[0];
    for (int i = 1; i < files.length; i++) {
       if (lastModifiedFile.lastModified() < files[i].lastModified()) {
           lastModifiedFile = files[i];
       }
    }
    return lastModifiedFile;
}
}
