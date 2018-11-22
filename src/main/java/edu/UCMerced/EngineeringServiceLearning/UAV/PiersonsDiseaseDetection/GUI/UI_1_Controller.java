package edu.UCMerced.EngineeringServiceLearning.UAV.PiersonsDiseaseDetection.GUI;

import edu.UCMerced.EngineeringServiceLearning.UAV.PiersonsDiseaseDetection.ImageAnalyzing;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class UI_1_Controller implements Initializable {

    private static String fileName;

    @FXML
    public ProgressBar barProgress;

    @FXML
    private HBox hbox;
    @FXML
    private TextField txtInputFile;
    @FXML
    private TextField txtOutputFile;

    public void initialize(URL url, ResourceBundle rb) {
        //Nothing
    }

    @FXML
    private void btnInputFile(MouseEvent event) {

        System.out.println("Input button working!");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Stitched Image Folder");

        Stage stage = (Stage) hbox.getScene().getWindow(); //GET STAGE OBJECT

        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("IMAGE files (*.png) (*.jpg) (*.jpeg) (*.tif) (*.tiff)", "*.png", "*.jpg", "*.jpeg", "*.tif", "*.tiff");
        fileChooser.getExtensionFilters().add(filter);

        System.setProperty("java.library.path", "/opencv/build/java/x64");
        File filePath = fileChooser.showOpenDialog(stage);

        if (fileChooser != null) {
            try {
                System.out.println(filePath.toString());
                System.out.println(filePath.getName());
                txtInputFile.setText(filePath.toString());
                fileName = filePath.getName();
            } catch (NullPointerException e) {
                System.out.println("ERROR NULL!");
            }
        }
    }

    @FXML
    private void btnOutputFile(MouseEvent event) {
        System.out.println("Output button working!");

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select where to save the NDVI image");
        Stage stage = (Stage) hbox.getScene().getWindow(); //GET STAGE OBJECT

        File selectedDirectory = directoryChooser.showDialog(stage);

        if (directoryChooser != null) {
            try {
                System.out.println(selectedDirectory.toString());
                txtOutputFile.setText(selectedDirectory.toString());
            } catch (NullPointerException e) {
                System.out.println("ERROR NULL!");
            }
        }
    }

    // UPON TASK FINISH
    // SET PROGRESS BAR TO 10 || UPDATE PROGRESS BAR

    @FXML
    private void btnAnalyze(MouseEvent event) {
        if (!(txtInputFile.getText().isEmpty() || txtOutputFile.getText().isEmpty())) {
            try {

                ImageAnalyzing t = new ImageAnalyzing(this);
                t.ImageAnalyze(fileName, txtInputFile.getText(), txtOutputFile.getText());

                System.out.println("PROGRESS BAR!");

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Please select options!");
        }
    }

}
