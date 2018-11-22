package edu.UCMerced.EngineeringServiceLearning.UAV.PiersonsDiseaseDetection;

import edu.UCMerced.EngineeringServiceLearning.UAV.PiersonsDiseaseDetection.GUI.UI_1_Controller;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImageAnalyzing {

    private final UI_1_Controller c;

    public ImageAnalyzing(UI_1_Controller controller) {
        this.c = controller;

    }

    public void ImageAnalyze(final String inputImageName, final String inputImageLocation, final String outputFolder) throws Exception {

        Task<Parent> task = new Task<Parent>() {
            @Override
            public Parent call() {

                //GET IMAGE TO PROCESS WHEN EVER
                String profileUser = System.getenv("userprofile");
                System.out.println("Found the user: " + profileUser);

                try {
                    Files.copy(Paths.get(inputImageLocation), Paths.get(outputFolder + "\\" + inputImageName));
                } catch (IOException e) {
                    System.out.println("IOException!");
                }
                System.out.println("Saved!");

                final File outputFile = new File(outputFolder + "\\" + inputImageName);

                final UAV_NDVI2 ndviObject = new UAV_NDVI2();

                Mat matObject2 = ndviObject.NDVIProcessing(outputFile.getPath(), true);
                if (matObject2 == null) {
                    return null;
                }
                Highgui.imwrite(outputFile.getPath(), matObject2);

                return null;
            }
        };

        c.barProgress.progressProperty().bind(task.progressProperty());

        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            public void handle(WorkerStateEvent event) {
                System.out.println("Finish task!");
                c.barProgress.progressProperty().unbind();
                c.barProgress.progressProperty().setValue(1.0);
            }
        });

        Thread thread = new Thread(task);
        thread.start();
    }
}
