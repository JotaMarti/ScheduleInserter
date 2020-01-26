package MODEL;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import scheduleinserter.ScheduleInserter;

public class Ventanas {

    private String fxmlName;
    private String tituloVentana;

    public Ventanas() {


        tituloVentana = "Schedule Inserter";

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ScheduleInserter.class.getResource("/VIEW/mainView.fxml"));
            // Cargo la ventana
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            // Seteo la scene y la muestro
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);

            stage.show();

            stage.setTitle(this.tituloVentana);
            stage.setResizable(false);
            //stage.getIcons().add(new Image("/images/mongo.png"));

            stage.setOnCloseRequest(e -> Platform.exit());

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
