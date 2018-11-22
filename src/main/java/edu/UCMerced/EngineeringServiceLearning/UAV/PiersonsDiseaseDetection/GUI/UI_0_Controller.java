package edu.UCMerced.EngineeringServiceLearning.UAV.PiersonsDiseaseDetection.GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UI_0_Controller implements Initializable {

    private static List<File> fileName;

    @FXML
    private HBox hbox;
    @FXML
    private TextField txtInputFile;

    public void initialize(URL url, ResourceBundle rb) {
        //Nothing
    }

    @FXML
    private void btnInputFile(MouseEvent event) {

        System.out.println("Input button working!");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select All Images to Stitch");

        Stage stage = (Stage) hbox.getScene().getWindow(); //GET STAGE OBJECT

        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("IMAGE files (*.png) (*.jpg) (*.jpeg) (*.tif) (*.tiff)", "*.png", "*.jpg", "*.jpeg", "*.tif", "*.tiff");
        fileChooser.getExtensionFilters().add(filter);

        fileName = fileChooser.showOpenMultipleDialog(stage);

        if (fileName != null) {
            try {
                txtInputFile.setText(fileName.toString());
            } catch (NullPointerException e) {
                System.out.println("ERROR NULL!");
            }
        }
    }

    @FXML
    private void btnAnalyze(MouseEvent event) throws IOException {

        if (!(txtInputFile.getText().isEmpty())) {
            try {

                File file = new File("blah.spj");  //Creates a .spj file in memory
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));

                //Writing inside the .spj file
                writer.write("<?xml version=\"1.0\" encoding =\"utf-8\"?>");
                writer.newLine();
                writer.write("<stitchProject version=\"2.0\" cameraMotion=\"automatic\">");
                writer.newLine();
                writer.write("<sourceImages>");

                for (int i = 0; i < fileName.size(); i++) {
                    System.out.println(fileName.get(i));
                    writer.write("<sourceImage filePath=\"" + fileName.get(i).getPath() + "\" />");
                    writer.newLine();
                }

                writer.write("</sourceImages>");
                writer.newLine();
                writer.write("</stitchProject>");
                writer.close();

                Runtime rt = Runtime.getRuntime();
                rt.exec("cmd.exe /c blah.spj"); //Opens ICE and then stitches the images listed in the .spj file

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Please select options!");
        }
    }

}
