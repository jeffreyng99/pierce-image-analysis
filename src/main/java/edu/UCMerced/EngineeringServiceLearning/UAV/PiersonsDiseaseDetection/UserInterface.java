package edu.UCMerced.EngineeringServiceLearning.UAV.PiersonsDiseaseDetection;

import org.apache.commons.io.FilenameUtils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Set;

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

            public void actionPerformed(ActionEvent e) {
                InputFile1.setText(FilePicker(e));
            }
        });
        OutputFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OutputFile1.setText(FileOutput(e));
            }
        });

        Analyze.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (InputFile1.getText().isEmpty() || OutputFile1.getText().isEmpty()) {
                    textPane1.setText("Pick folders, human.");
                    return;
                }
                    new Thread()
                    {
                        public void run()
                        {
                            try
                            {
                                thisDoesPrettyMuchEverything(InputFile1.getText(), OutputFile1.getText());
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
                    }.start();

                }
            });}


    private String FilePicker(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        System.setProperty("java.library.path", "/opencv/build/java/x64");
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
        try
        {
            String fullPath = ExportResource("/opencv_java2411.dll");
            System.load(fullPath);
        }
        catch (Exception e)
        {

        }
    }

    /**
     * Export a resource embedded into a Jar file to the local file path.
     *
     * @param resourceName ie.: "/SmartLibrary.dll"
     * @return The path to the exported resource
     * @throws Exception
     */
    static public String ExportResource(String resourceName) throws Exception {
        InputStream stream = null;
        OutputStream resStreamOut = null;
        String jarFolder;
        try {
            stream = UserInterface.class.getResourceAsStream(resourceName);//note that each / is a directory down in the "jar tree" been the jar the root of the tree
            if(stream == null) {
                throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
            }

            int readBytes;
            byte[] buffer = new byte[4096];
            jarFolder = new File(UserInterface.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/');
            resStreamOut = new FileOutputStream(jarFolder + resourceName);
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            stream.close();
            resStreamOut.close();
        }

        return jarFolder + resourceName;
    }

    public void thisDoesPrettyMuchEverything(String inputFolder, String outputFolder) throws IOException, InterruptedException {
        String Instruct = "Hello";
        String stitchedImage="";
        //In other words, useless stuff. take code from uaveWindowed function that isn't GUI stuff.
        SetText("Microsoft ICE will now open, \n" +
                "go through each step and click on 'Next', which can be found in the top right corner. \n" +
                "When in step 4 click on 'Export to disk', and then click save." ); //letting user know that the program used to stitch the images will open on its own
        //Telling user to move through the the steps by clicking next in microsoft ICE
        //Telling user to export the stitched image into the default folder
        File file = new File("blah.spj");  //Creates a .spj file in memory
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        //Writing inside the .spj file
        writer.write("<?xml version=\"1.0\" encoding =\"utf-8\"?>"); writer.newLine();
        writer.write("<stitchProject version=\"2.0\" cameraMotion=\"automatic\">"); writer.newLine();
        writer.write("<sourceImages>"); writer.newLine();
        //Writing the location of the images in the .spj file
        File inputImages = new File(inputFolder);
        File[] listOfFiles = inputImages.listFiles();
        for (int i=0; i < listOfFiles.length; i++)
        {
            if (listOfFiles[i].isFile())
            {
                writer.write("<sourceImage filePath=\"" + inputFolder + "\\" + listOfFiles[i].getName() + "\" />"); writer.newLine();
            }
        }
        writer.write("</sourceImages>"); writer.newLine();
        writer.write("</stitchProject>");
        writer.close();

        Runtime rt = Runtime.getRuntime();
        rt.exec("cmd.exe /c blah.spj"); //Opens ICE and then stitches the images listed in the .spj file. We would change this line if we were to stitch with another program
        //This should open up ICE and wait for the user to start the actual stitching process. The user can press next for each step in ICE. ICE will save the stitched image
        //under the name of the first image with a _stitch added to the end. It will also save this file in the documents folder by default.
        String imageName = listOfFiles[0].getName();
        imageName = FilenameUtils.removeExtension(imageName);
        stitchedImage = userprofile + "\\Documents"+"\\"+imageName+"_stitch.jpg";
        File stitchedImageFile = new File(stitchedImage);
        File outputImages = new File(outputFolder);
        File[] listofFilesDocuments = stitchedImageFile.listFiles(); System.out.println("Pass text one");
        if (stitchedImageFile.exists()) {
            stitchedImageFile.delete();
        System.out.println("Pass text two"); }
        File outputFile = new File(outputFolder+"\\"+imageName+"_stitch.jpg");
        System.out.println(outputFile);
        if (outputFile.exists()) {
            outputFile.delete();
        System.out.println("Pass text three"); }
        new File(outputFolder).mkdir();
        while (!stitchedImageFile.exists())
        {
            Thread.sleep(3000);
            System.out.print("Error Number One");
        }


        while (!stitchedImageFile.renameTo(outputFile))
        {
            Thread.sleep(1000);
            System.out.print("Error Number Two");
        }
        SetText("Stitching Complete, now running NDVI algorithm");
        rt.exec("cmd.exe /c Taskkill /IM ICE.exe /F"); //closes ICE
        //Run NDVI function with the stitched image with outputFolder

        System.out.println("debug 1");
        UAV_NDVI2 ndviObject = new UAV_NDVI2();
        System.out.println("debug 2"); //ends here
         
	        Mat matObject2 = ndviObject.NDVIProcessing(outputFile.getPath(), true); // error here
        System.out.println("debug 3");
	           SetText("NDVI algorithm!");
	        
	        if (matObject2 == null)
	        {
	            SetText("Error finding stitched image");
	            return;
	        }
	        Highgui.imwrite(outputFolder, matObject2);//See Gitlab issue tracker
	        SetText("Done. You can find the image in " + outputFolder);
     



    }

}
