package edu.UCMerced.EngineeringServiceLearning.UAV.PiersonsDiseaseDetection;

import org.apache.commons.io.FilenameUtils;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import javax.swing.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Created by Buraaq Alrawi on 10/11/2016.
 */
public class UserInterface {

    private JPanel Jpanel;

    private JButton btnInputFile;
    private JButton btnOutputFile;
    private JButton btnAnalyze;

    private JTextField txtInputFile;
    private JTextField txtOutputFile;

    private JTextPane txpError;
    private JButton button1;
    private JTextField textField1;
    private JTextField textField2;
    private JButton button2;

    public UserInterface() {
        btnInputFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtInputFile.setText(FilePicker(e));
            }
        });
        btnOutputFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtOutputFile.setText(FileOutput(e));
            }
        });

        btnAnalyze.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (txtInputFile.getText().isEmpty() || txtOutputFile.getText().isEmpty()) {
                    txpError.setText("Select the folder(s) location!");
                    return;
                }
                    new Thread()
                    {
                        public void run()
                        {
                            try
                            {
                                OnBtnAnalyze(txtInputFile.getText(), txtOutputFile.getText());
                            }
                            catch(IOException ex)
                            {
                                JOptionPane.showMessageDialog(null,"Error,IOException");
                                txpError.setText(ex.getMessage());
                            }
                            catch (InterruptedException ex)
                            {
                                txpError.setText(ex.getMessage());
                                JOptionPane.showMessageDialog(null,"Error,InterruptedException");
                            }

                        }
                    }.start();

                }
            });}


    private String FilePicker(ActionEvent e) { // Allows for the selecting of a folder where images are.
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        System.setProperty("java.library.path", "/opencv/build/java/x64");
        int returnValue = fileChooser.showDialog(null, "Select folder with images!");
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getPath();
        } else {
            return "Select folder with images!";
        }
    }
    public void SetText(String instruct){
        txpError.setText(instruct);
    }

    private String FileOutput(ActionEvent e){ // Allows for the selecting of folder to save NDVI stitch.
        JFileChooser fileChooser1 = new JFileChooser();
        fileChooser1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = fileChooser1.showDialog(null, "Select folder to save NDVI image!");
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return fileChooser1.getSelectedFile().getPath();
        } else{
            return "Select folder to save NDVI image!";
        }
    }


    public static void main(String[] args) { //Creating the user GUI and exporting the
        JFrame frame = new JFrame("UserInterface");
        frame.setContentPane(new UserInterface().Jpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        try
        {
            String fullPath = ExportResource("/opencv_java2411.dll"); // Outputs necessary .dll to use opencv library.
            System.load(fullPath);

            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            }
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
            stream = UserInterface.class.getResourceAsStream(resourceName);
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

    public void OnBtnAnalyze(String inputFolder, String outputFolder) throws IOException, InterruptedException {

        String stitchedImage;
        SetText("Microsoft ICE now launching...Please select the last step at the top right corner!");
        File file = new File("blah.spj");  //Creates a .spj file in memory
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        //Writing inside the .spj file
        writer.write("<?xml version=\"1.0\" encoding =\"utf-8\"?>"); writer.newLine();
        writer.write("<stitchProject version=\"2.0\" cameraMotion=\"automatic\">"); writer.newLine();
        writer.write("<sourceImages>"); writer.newLine();

        //Writing the location of the images in the .spj file
        File inputImages = new File(inputFolder);
        File[] listOfFiles = inputImages.listFiles();

        List<String> extImages = new ArrayList<String>(); //Image extensions array (D.A.C)
        extImages.add(".jpg");
        extImages.add(".png");
        extImages.add(".jpeg");
        extImages.add(".tiff");

        for (int i=0; i < listOfFiles.length; i++) {
            for (String extensions : extImages) {
                    if (listOfFiles[i].isFile() && listOfFiles[i].getName().toLowerCase().endsWith(extensions)) {
                       // if (listOfFiles[i].isFile() && listOfFiles[i].getName().matches("_002")) {
                            System.out.print(listOfFiles[i].getName());
                            writer.write("<sourceImage filePath=\"" + inputFolder + "\\" + listOfFiles[i].getName() + "\" />");
                            writer.newLine();
                    }
            }
        }

        writer.write("</sourceImages>"); writer.newLine();
        writer.write("</stitchProject>");
        writer.close();

        Runtime rt = Runtime.getRuntime();
        rt.exec("cmd.exe /c blah.spj"); //Opens ICE and then stitches the images listed in the .spj file

        String imageName = listOfFiles[0].getName();
        imageName = FilenameUtils.removeExtension(imageName);

        String profileUser = System.getenv("userprofile");
        stitchedImage = profileUser + "\\Documents"+"\\"+imageName+"_stitch.jpg";

        File stitchedImageFile = new File(stitchedImage);

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
        SetText("Stitching Complete, now running NDVI algorithm");
        rt.exec("cmd.exe /c Taskkill /IM ICE.exe /F"); //closes ICE
        //Run NDVI function with the stitched image with outputFolder


        UAV_NDVI2 ndviObject = new UAV_NDVI2();
         
	        Mat matObject2 = ndviObject.NDVIProcessing(outputFile.getPath(), true); // error here
	           SetText("NDVI algorithm!");
	        
	        if (matObject2 == null)
	        {
	            SetText("Error finding stitched image");
	            return;
	        }
	        Highgui.imwrite(stitchedImage, matObject2);
	        SetText("Done. You can find the image in " + outputFolder);

	        //System.exit(0); //Do not close APP

    }
}
