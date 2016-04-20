package edu.UCMerced.EngineeringServiceLearning.UAV.PiersonsDiseaseDetection;

import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by txrrob on 4/19/2016.
 */
public class GUItest {

    private JPanel panel1;
    private JTextField inputFolderField;
    private JButton browseButton;
    private JTextField outputFolderField;
    private JButton browseButton1;
    private JButton startAnalysisButton;

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public void main(String[] args) throws IOException, InterruptedException {


    }
    public void startAnalysisMethod() throws IOException, InterruptedException {
        String inputFolder = inputFolderField.getText();
        String outputFolder = outputFolderField.getText();
        String stitchedImage = "";
        //In other words, useless stuff. take code from uaveWindowed function that isn't GUI stuff.
        System.out.println("Currently stitching images");
        File file = new File("blah.spj");  //Creates a .spj file in memory
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        //Writing inside the .spj file
        writer.write("<?xml version=\"1.0\" encoding =\"utf-8\"?>");
        writer.newLine();
        writer.write("<stitchProject version=\"2.0\" cameraMotion=\"automatic\">");
        writer.newLine();
        writer.write("<sourceImages>");
        //Writing the location of the images in the .spj file
        File inputImages = new File(inputFolder);
        File[] listOfFiles = inputImages.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                writer.newLine();
                writer.write("<sourceImage filePath=\"" + inputFolder + "\\" + listOfFiles[i].getName() + "\" />");
            }
        }
        writer.newLine();
        writer.write("</sourceImages>");
        writer.newLine();
        writer.write("</stitchProject>");
        writer.close();

        Runtime rt = Runtime.getRuntime();
        rt.exec("cmd.exe /c blah.spj"); //Opens ICE and then stitches the images listed in the .spj file. We would change this line if we were to stitch with another program
        //This should open up ICE and wait for the user to start the actual stitching process. The user can press next for each step in ICE. ICE will save the stitched image
        //under the name of the first image with a _stitch added to the end. It will also save this file in the documents folder by default.
        String imageName = listOfFiles[0].getName();
        imageName = FilenameUtils.removeExtension(imageName);
        String userprofile = System.getenv("userprofile");
        stitchedImage = userprofile + "\\Documents" + "\\" + imageName + "_stitch.jpg";
        File documentsFolder = new File(stitchedImage);
        File outputImages = new File(outputFolder);
        File[] listofFilesDocuments = documentsFolder.listFiles();
        while (!documentsFolder.exists()) {
            Thread.sleep(3000);
            System.out.println(stitchedImage);
        }
        System.out.println("Stitching Found");
        documentsFolder.renameTo(new File(outputFolder + "\\" + imageName + "_stitch.jpg"));
        //Run NDVI function with the stitched image with outputFolder
    }
}
