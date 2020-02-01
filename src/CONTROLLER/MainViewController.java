package CONTROLLER;

import MODEL.Alertas;
import MODEL.CopyScene;
import MODEL.ParseInputTest;
import MODEL.ReadAndWrite;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import java.io.File;
import java.io.FilenameFilter;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class MainViewController implements Initializable {

    @FXML
    private TextArea textArea;
    @FXML
    private Button btnSet;
    @FXML
    private TextField txtField;
    @FXML
    private ChoiceBox<String> cbOptions;
    @FXML
    private TextField txtFieldFirstScene;
    @FXML
    private TextField txtFieldSecondScene;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeChoicebox();
    }

    @FXML
    private void clickSet(ActionEvent event) {

        String key = txtField.getText();
        String cb = cbOptions.getValue();
        String firstScene = txtFieldFirstScene.getText();
        String secondScene = txtFieldSecondScene.getText();

        if (cb.equals("Sustituir")) {
            if (key.isEmpty() || textArea.getLength() == 0) {
                Alertas.alertaError("Por favor complete ambos campos");
            } else {
                ParseInputTest input = new ParseInputTest(textArea.getText());
                String[] allDirectories = getAllFolders();
                int contadorUnidades = ContadorUnidades(allDirectories);

                for (int i = 0; i < contadorUnidades; i++) {
                    File lastModifiedFile = getLatestFilefromDir("C:\\Ngaro\\Runtimes\\Unidad-" + i + "\\backup");
                    ReadAndWrite.click(input.getDocument(), key, lastModifiedFile);

                }
                Alertas.alertaInfo("Etiqueta sustituida correctamente!");
            }
        }

        if (cb.equals("Añadir Escena")) {
            if (firstScene.isEmpty() || secondScene.isEmpty()) {
                Alertas.alertaError("Por favor complete ambos campos");
            } else {
                String[] allDirectories = getAllFolders();
                int contadorUnidades = ContadorUnidades(allDirectories);

                for (int i = 0; i < contadorUnidades; i++) {
                    File lastModifiedFile = getLatestFilefromDir("C:\\Ngaro\\Runtimes\\Unidad-" + i + "\\backup");
                    CopyScene.click(firstScene, secondScene, lastModifiedFile);

                }
                Alertas.alertaInfo("Escena añadida correctamente!");
            }
        }

    }

    private File getLatestFilefromDir(String dirPath) {
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

    private String[] getAllFolders() {
        File allFolders = new File("C:\\Ngaro\\Runtimes\\");
        String[] allDirectories = allFolders.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        return allDirectories;
    }

    private int ContadorUnidades(String[] allDirectories) {
        int contador = 0;
        String regexUnidades = "Unidad-\\d*";
        for (int i = 0; i < allDirectories.length; i++) {
            if (allDirectories[i].matches(regexUnidades)) {
                contador++;
            }

        }
        return contador;
    }

    public void initializeChoicebox() {
        txtFieldFirstScene.setDisable(true);
        txtFieldSecondScene.setDisable(true);
        txtField.setDisable(false);
        cbOptions.getItems().add("Sustituir");
        cbOptions.getItems().add("Añadir Escena");
        cbOptions.getSelectionModel().selectFirst();
        cbOptions.getSelectionModel()
                .selectedItemProperty()
                .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> actualizaChoiceBox(newValue));
    }

    private void actualizaChoiceBox(String value) {
        if (value.equals("Sustituir")) {
            txtFieldFirstScene.setDisable(true);
            txtFieldSecondScene.setDisable(true);
            txtField.setDisable(false);
            textArea.setDisable(false);
        } else {
            txtFieldFirstScene.setDisable(false);
            txtFieldSecondScene.setDisable(false);
            txtField.setDisable(true);
            textArea.setDisable(true);
        }

    }
}
