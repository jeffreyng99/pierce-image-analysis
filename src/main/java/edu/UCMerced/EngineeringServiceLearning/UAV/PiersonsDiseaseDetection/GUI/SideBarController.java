package edu.UCMerced.EngineeringServiceLearning.UAV.PiersonsDiseaseDetection.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SideBarController implements Initializable {

    @FXML
    private BorderPane borderpane;

    @FXML
    private Button btn0, btn1, btn2, btn3, btn4;

    public void initialize(URL url, ResourceBundle rb) {
        loadUI("UI_3.fxml");
    }

    @FXML
    private void close(MouseEvent event) {
        Stage stage = (Stage) borderpane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void UI_0(MouseEvent event) {
        borderpane.setCenter(null);  //REMOVES THE CENTER ELEMENTS
        loadUI("UI_0.fxml");
        btn0.setStyle("-fx-text-fill: white; -fx-background-color: #17a2b8; -fx-background-radius: 0");
        btn1.setStyle("-fx-text-fill: gray; -fx-background-color: transparent; -fx-background-radius: 0");
        btn2.setStyle("-fx-text-fill: gray; -fx-background-color: transparent; -fx-background-radius: 0");
        btn3.setStyle("-fx-text-fill: gray; -fx-background-color: transparent; -fx-background-radius: 0");
        btn4.setStyle("-fx-text-fill: gray; -fx-background-color: transparent; -fx-background-radius: 0");

    }

    @FXML
    private void UI_1(MouseEvent event) {
        loadUI("UI_1.fxml");
        btn1.setStyle("-fx-text-fill: white; -fx-background-color: #17a2b8; -fx-background-radius: 0");
        btn0.setStyle("-fx-text-fill: gray; -fx-background-color: transparent; -fx-background-radius: 0");
        btn2.setStyle("-fx-text-fill: gray; -fx-background-color: transparent; -fx-background-radius: 0");
        btn3.setStyle("-fx-text-fill: gray; -fx-background-color: transparent; -fx-background-radius: 0");
        btn4.setStyle("-fx-text-fill: gray; -fx-background-color: transparent; -fx-background-radius: 0");
        //test.setStyle("-fx-background-color: black;");
    }

    @FXML
    private void UI_2(MouseEvent event) {
        loadUI("UI_2.fxml");
        btn2.setStyle("-fx-text-fill: white; -fx-background-color: #17a2b8; -fx-background-radius: 0");
        btn0.setStyle("-fx-text-fill: gray; -fx-background-color: transparent; -fx-background-radius: 0");
        btn1.setStyle("-fx-text-fill: gray; -fx-background-color: transparent; -fx-background-radius: 0");
        btn3.setStyle("-fx-text-fill: gray; -fx-background-color: transparent; -fx-background-radius: 0");
        btn4.setStyle("-fx-text-fill: gray; -fx-background-color: transparent; -fx-background-radius: 0");
    }

    @FXML
    private void UI_3(MouseEvent event) {
        loadUI("UI_3.fxml");
        btn3.setStyle("-fx-text-fill: white; -fx-background-color: #17a2b8; -fx-background-radius: 0");
        btn0.setStyle("-fx-text-fill: gray; -fx-background-color: transparent; -fx-background-radius: 0");
        btn1.setStyle("-fx-text-fill: gray; -fx-background-color: transparent; -fx-background-radius: 0");
        btn2.setStyle("-fx-text-fill: gray; -fx-background-color: transparent; -fx-background-radius: 0");
        btn4.setStyle("-fx-text-fill: gray; -fx-background-color: transparent; -fx-background-radius: 0");
    }

    @FXML
    private void UI_4(MouseEvent event) {
        loadUI("UI_4.fxml");
        btn4.setStyle("-fx-text-fill: white; -fx-background-color: #17a2b8; -fx-background-radius: 0");
        btn0.setStyle("-fx-text-fill: gray; -fx-background-color: transparent; -fx-background-radius: 0");
        btn1.setStyle("-fx-text-fill: gray; -fx-background-color: transparent; -fx-background-radius: 0");
        btn2.setStyle("-fx-text-fill: gray; -fx-background-color: transparent; -fx-background-radius: 0");
        btn3.setStyle("-fx-text-fill: gray; -fx-background-color: transparent; -fx-background-radius: 0");
    }

    public void loadUI(String ui) {
        Parent root = null;

        try {
            //URL url = Paths.get("./src/main/resources/" + ui).toUri().toURL();
            root = FXMLLoader.load(getClass().getClassLoader().getResource(ui));
        } catch (IOException ex) {
            Logger.getLogger(SideBarController.class.getName()).log(Level.SEVERE, null, ex);
        }
        borderpane.setCenter(root);
    }
}
