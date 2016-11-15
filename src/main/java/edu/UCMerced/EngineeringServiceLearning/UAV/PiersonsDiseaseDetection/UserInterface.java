package edu.UCMerced.EngineeringServiceLearning.UAV.PiersonsDiseaseDetection;

import org.apache.commons.io.FilenameUtils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Buraaq Alrawi on 10/11/2016.
 */
public class UserInterface {
    private JButton OpenFileButton;
    private JTextField InputFile1;
    private JPanel Jpanel;
    private JTextField OutputFile1;
    private JButton OutputFileButton;
    private JButton Analyze;
    private JTextPane textPane1;

    public UserInterface() {
        OpenFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InputFile1.setText(FilePicker(e));
            }
        });
        OutputFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OutputFile1.setText(FileOutput(e));
            }
        });
        Analyze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { textPane1.setText("Analyzing");
                try
                {
                    console(InputFile1.getText(), OutputFile1.getText());
                }
                catch(IOException ex)
                {
                    JOptionPane.showMessageDialog(null,"Error,IOException");
                    textPane1.setText(ex.getMessage());
                }
                catch (InterruptedException ex)
                {
                    textPane1.setText(ex.getMessage());
                    JOptionPane.showMessageDialog(null,"Error,InterruptedException");
                }
                }
        });
        
    }


    private String FilePicker(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = fileChooser.showDialog(null, "Choose Input Folder");
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getPath();
        } else {
            return "Pick a folder.";
        }
    }
    public void SetText(String instruct){
        textPane1.setText(instruct);
    }

    private String FileOutput(ActionEvent e){
        JFileChooser fileChooser1 = new JFileChooser();
        fileChooser1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = fileChooser1.showDialog(null, "Choose Output Folder");
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return fileChooser1.getSelectedFile().getPath();
        } else{
            return "Pick output folder.";
        }
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("UserInterface");
        frame.setContentPane(new UserInterface().Jpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    public void console(String inputFolder,String outputFolder) throws IOException, InterruptedException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); //this is needed to remove an unsatisfied link error
        String Instruct = "Hello";
        String stitchedImage="";
        //In other words, useless stuff. take code from uaveWindowed function that isn't GUI stuff.
        Scanner input = new Scanner(System.in);
        inputFolder = input.nextLine();     //Input the name of the folder containing the pictures
        outputFolder = input.nextLine();    //Input the name of the folder in which you want to save the analyzed images
        SetText("Microsoft ICE will now open, \n" +
                "go through each step and click on 'Next', which can be found in the top right corner. \n" +
                "When in step 4 click on 'Export to disk', and then click save." ); //letting user know that the program used to stitch the images will open on its own
        //Telling user to move through the the steps by clicking next in microsoft ICE
        //Telling user to export the stitched image into the default folder
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
        for (int i=0; i < listOfFiles.length; i++)
        {
            if (listOfFiles[i].isFile())
            {
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
        String imageName= listOfFiles[0].getName();
        imageName = FilenameUtils.removeExtension(imageName);
        String userprofile = System.getenv("userprofile");
        stitchedImage= userprofile + "\\Documents"+"\\"+imageName+"_stitch.jpg";
        File stitchedImageFile = new File(stitchedImage);
        File outputImages = new File(outputFolder);
        File[] listofFilesDocuments = stitchedImageFile.listFiles();
        if (stitchedImageFile.exists())
            stitchedImageFile.delete();
        File outputFile = new File(outputFolder+"\\"+imageName+"_stitch.jpg");
        if (outputFile.exists())
            outputFile.delete();
        new File(outputFolder).mkdir();
        while (!stitchedImageFile.exists())
        {
            Thread.sleep(3000);
        }


        while (!stitchedImageFile.renameTo(outputFile))
        {
            Thread.sleep(1000);
        }
        System.out.println("Stitching Complete");
        rt.exec("cmd.exe /c Taskkill /IM ICE.exe /F"); //closes ICE
        //Run NDVI function with the stitched image with outputFolder


        UAV_NDVI2 ndviObject = new UAV_NDVI2();
        Mat matObject2 = ndviObject.NDVIProcessing(outputFile.getPath(), true);
        if (matObject2 == null)
        {
            System.out.println("Error finding stitched image");
            return;
        }
        Highgui.imwrite(outputFolder, matObject2);




//        Process process = new ProcessBuilder(inputFolder);
//        InputStream is = process.getInputStream();
//        InputStreamReader isr = new InputStreamReader(is);
//        BufferedReader br = new BufferedReader(isr);
//        String line;

//        System.out.printf("Output of running %s is:", Arrays.toString(args));
//
//        while ((line = br.readLine()) != null) {
//            System.out.println(line);
//        }

        /*
        This tells the program to take the pictures from the folder and stitch them together

        String command = "cmd /c start cmd.exe /K \"cd "+dir1.getAbsolutePath()+"\\src\\Scripts && Analysis";
        Start ""  "C:\
        String start="Start \"\"  \"";
        String command = "cmd /c "+start+" \"cd "+dir1.getAbsolutePath()+"\\src\\Scripts && Analysis";
        try {
        Process child = Runtime.getRuntime().exec(command);
        } catch (IOException ex) {
        Logger.getLogger(uavWindowed.class.getName()).log(Level.SEVERE, null, ex);
        }

        make command = inputFolder?
        */


        //System.out.println("startAnalysis: " + me.startAnalysis);
        //System.out.println("pathFile: " + me.pathFile);

    }





}
