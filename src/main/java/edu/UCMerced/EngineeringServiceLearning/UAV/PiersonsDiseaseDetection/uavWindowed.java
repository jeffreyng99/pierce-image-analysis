package edu.UCMerced.EngineeringServiceLearning.UAV.PiersonsDiseaseDetection;

import java.awt.BorderLayout;
import java.awt.Color;
import static java.awt.Color.GREEN;
import static java.awt.Color.WHITE;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

//OpenCV imports
import org.opencv.core.*;
import org.opencv.highgui.*;
import org.opencv.imgproc.Imgproc;

import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Color;
import static java.awt.Color.BLACK;
import static java.awt.Color.RED;
import static java.awt.Color.white;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class uavWindowed extends JFrame {
    
    Color customColor; 
    
    int started = 0;

    int startAnalysis = 0;
        String currentPattern;

    //Will the names for our images, input.jpg, output.jpg. These will be added to the end of our directories
    String pathFile;
    String pathFile2;
    
     String FileInLocation="";
     String FileOutLocation;


    //Creates all of our panels which hold the content on each page
    JPanel homePanel = new JPanel();
    JPanel startPanel = new JPanel();

    JPanel step1 = new JPanel();
    JPanel step2 = new JPanel();
    JPanel step3 = new JPanel(new BorderLayout());
    JPanel step4 = new JPanel();
    JPanel step5 = new JPanel();

    JPanel testPanel = new JPanel();
    JPanel imageMacro = new JPanel();

    JPanel imagePanel = new JPanel();
    JPanel imagePanel2 = new JPanel();

    JPanel stitchBackground = new JPanel();
       JPanel analyzeBackground = new JPanel();

    JPanel leftPanel = new JPanel();
          JPanel largeView = new JPanel();

          JLabel stitchedImg;

//           JPanel scrollPane = new JPanel();
//           JScrollPane scrollFrame = new JScrollPane();

    //Creates the sub-menus for our menus. Each is a JMenu item which is then added to our menubar item
    JMenu startScreen = new JMenu("Start");
    JMenu s1 = new JMenu("Step 1");
    JMenu s2 = new JMenu("Step 2");
    JMenu s3 = new JMenu("Step 3");
    JMenu s4 = new JMenu("Step 4");
    JMenu s5 = new JMenu("Step 5");


     JPanel insideMacro = new JPanel();

    //Creates our text fields as a JLabel and JTextField which accepts text input and JLabel is an element which can be put into a JPanel.
    JLabel stitchJLabel = new JLabel();
    JTextField path = new JTextField();

    JLabel stitchJLabel2 = new JLabel();
    JTextField path2 = new JTextField();


    JLabel scriptText = new JLabel();

     JComboBox stitchedImageViewer;
      JComboBox analyzedImageViewer;

     JScrollPane scroll = new JScrollPane();



     JLabel stitchViewLabel;
       JLabel analyzedViewLabel;
    String badPercent;
    String channels;
    String totalPixels;
    String modifyCount;

//      JButton jButton1 = new JButton();
//
//                JLabel label = new JLabel("Label");
//
//    JScrollPane jScrollPane = new JScrollPane(label);






    ImageIcon home = new ImageIcon("Home.png");
    ImageIcon aboutUS = new ImageIcon("uavStart.png");

    JLabel aboutP = new JLabel(aboutUS);

    JPanel aboutPage = new JPanel();


    //Boolean operators, checks if our menu has been clicked or not.
    boolean s1Check;
    boolean s2Check;
    boolean s3Check;
    boolean s4Check;
    boolean s5Check;

    public uavWindowed() throws IOException {

        setTitle("Engineering Service Learning: Crop Analysis 1.0");
        setSize(1250, 880);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

       final Border blackline, raisedetched, loweredetched,
       raisedbevel, loweredbevel, empty, compound;

       blackline = BorderFactory.createLineBorder(Color.black);
raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
raisedbevel = BorderFactory.createRaisedBevelBorder();
loweredbevel = BorderFactory.createLoweredBevelBorder();
empty = BorderFactory.createEmptyBorder();

compound = BorderFactory.createCompoundBorder(
                          raisedbevel, loweredbevel);

 //testPanel.setBorder(compound);
 imageMacro.setBorder(compound);


 insideMacro.setBorder(compound);

 //largeView.setBorder(compound);

//  jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//    jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//    jScrollPane.setViewportBorder(new LineBorder(Color.RED));
//    jScrollPane.getViewport().add(jButton1, null);




        // Creates a menubar for a JFrame---------------------------------------------------------------------------------------------------------
        JMenuBar menuBar = new JMenuBar();

        // Add the menubar to the frame---------------------------------------------------------------------------------------------------------
        setJMenuBar(menuBar);

        //Creates Menu tabs to add to the main window---------------------------------------------------------------------------------------------------------
        //Adds the menu tabs to the menuBar---------------------------------------------------------------------------------------------------------
        menuBar.add(startScreen);
        menuBar.add(s1);
        menuBar.add(s2);
        menuBar.add(s3);
        menuBar.add(s4);
        menuBar.add(s5);

        //Change color to grey if inactive, green active---------------------------------------------------------------------------------------------------------
        startScreen.setOpaque(true);
        startScreen.setBackground(Color.GREEN);

        s1.setOpaque(true);
        s1.setBackground(Color.LIGHT_GRAY);

        s2.setOpaque(true);
        s2.setBackground(Color.LIGHT_GRAY);

        s3.setOpaque(true);
        s3.setBackground(Color.LIGHT_GRAY);

        s4.setOpaque(true);
        s4.setBackground(Color.LIGHT_GRAY);

        s5.setOpaque(true);
        s5.setBackground(Color.LIGHT_GRAY);

        //Create sub-menus---------------------------------------------------------------------------------------------------------
        JMenuItem about = new JMenuItem("About");
        JMenuItem help = new JMenuItem("Help");
        JMenuItem exit = new JMenuItem("Exit");
        JMenuItem start = new JMenuItem("Start Program");

        //Add sub-menus to main menu and separators---------------------------------------------------------------------------------------------------------
        startScreen.add(start);
        startScreen.add(new JSeparator());
        startScreen.add(help);
        startScreen.add(new JSeparator());
        startScreen.add(about);
        startScreen.add(new JSeparator());
        startScreen.add(exit);

        //Start Screen clicked events---------------------------------------------------------------------------------------------------------
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);

            }
        });

        //Main Menus clicked events
        s1.addMenuListener(new MenuListener() {

            public void menuSelected(MenuEvent e) {
                //System.out.println("menuSelected");



                if (s1.getBackground() == GREEN) {

                } else {
                    s1.setBackground(GREEN);
                }

                uavWindowed.this.getContentPane().removeAll();
                uavWindowed.this.getContentPane().add(step1);
                uavWindowed.this.getContentPane().revalidate();

                File dir1 = new File (".");


                String command = "cmd /c start cmd.exe /K \"cd "+dir1.getAbsolutePath()+"\\src\\Scripts && Analysis";
                //Start ""  "C:\

                String start="Start \"\"  \"";
//String command = "cmd /c "+start+" \"cd "+dir1.getAbsolutePath()+"\\src\\Scripts && Analysis";
                try {
                    Process child = Runtime.getRuntime().exec(command);
                } catch (IOException ex) {
                    Logger.getLogger(uavWindowed.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            public void menuDeselected(MenuEvent e) {
                //System.out.println("menuDeselected");

            }


            public void menuCanceled(MenuEvent e) {
                //System.out.println("menuCanceled");

            }
        });

        s2.addMenuListener(new MenuListener() {


            public void menuSelected(MenuEvent e) {
                //System.out.println("menuSelected");

                if (s1.getBackground() == GREEN) {
                    s2.setBackground(GREEN);
                }
                uavWindowed.this.getContentPane().removeAll();
                uavWindowed.this.getContentPane().add(step2);
                uavWindowed.this.getContentPane().revalidate();
            }


            public void menuDeselected(MenuEvent e) {
                //System.out.println("menuDeselected");

            }


            public void menuCanceled(MenuEvent e) {
                //System.out.println("menuCanceled");

            }
        });

        s3.addMenuListener(new MenuListener() {


            public void menuSelected(MenuEvent e) {
                //System.out.println("menuSelected");

                if (s2.getBackground() == GREEN) {
                    s3.setBackground(GREEN);
                }
                uavWindowed.this.getContentPane().removeAll();
                uavWindowed.this.getContentPane().add(step3);
                uavWindowed.this.getContentPane().revalidate();
            }


            public void menuDeselected(MenuEvent e) {
                //System.out.println("menuDeselected");

            }


            public void menuCanceled(MenuEvent e) {
                //System.out.println("menuCanceled");

            }
        });

        s4.addMenuListener(new MenuListener() {


            public void menuSelected(MenuEvent e) {
                //System.out.println("menuSelected");

                if (s3.getBackground() == GREEN) {
                    s4.setBackground(GREEN);
                }
                uavWindowed.this.getContentPane().removeAll();
                uavWindowed.this.getContentPane().add(step4);
                uavWindowed.this.getContentPane().revalidate();

            }


            public void menuDeselected(MenuEvent e) {
                //System.out.println("menuDeselected");

            }


            public void menuCanceled(MenuEvent e) {
                //System.out.println("menuCanceled");

            }
        });

        s5.addMenuListener(new MenuListener() {


            public void menuSelected(MenuEvent e) {
                //System.out.println("menuSelected");

                if (s4.getBackground() == GREEN) {
                    s5.setBackground(GREEN);
                }
                uavWindowed.this.getContentPane().removeAll();
                uavWindowed.this.getContentPane().add(step5);
                uavWindowed.this.getContentPane().revalidate();
            }


            public void menuDeselected(MenuEvent e) {
                //System.out.println("menuDeselected");

            }


            public void menuCanceled(MenuEvent e) {
                //System.out.println("menuCanceled");

            }
        });

//Creates new JPanel, holds all page components-------------------------------------------------------------------------------------------
        startPanel.setBackground(WHITE);
        aboutPage.add(aboutP);

        JPanel input = new JPanel();

        JPanel scriptTextLabel = new JPanel();

        input.setLayout(new BoxLayout(input, BoxLayout.X_AXIS));
        insideMacro.setLayout(new BoxLayout(insideMacro, BoxLayout.Y_AXIS));

        scriptText.setText("This script has now started an automated process. Please complete the remaining steps.");
        scriptText.setForeground(white);

        scriptTextLabel.setBackground(new Color(72,120,120));
        scriptTextLabel.add(scriptText);

        //step1.setBackground(new Color(72,120,120));
        step1.add(scriptTextLabel);
       // step1.add(scriptText);




        step2.setLayout(new BoxLayout(step2, BoxLayout.Y_AXIS));
        step2.setBorder(raisedbevel);





          testPanel.setLayout(new BoxLayout(testPanel, BoxLayout.X_AXIS));

          imageMacro.setLayout(new BoxLayout(imageMacro, BoxLayout.Y_AXIS));
          leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));




          imageMacro.setBackground(new Color(72,120,120));
           insideMacro.setBackground(new Color(99,166,166));

            leftPanel.add(imageMacro, new BoxLayout(testPanel, BoxLayout.Y_AXIS));

            imageMacro.setMaximumSize(new Dimension(1000, 1000));

            largeView.setBackground(WHITE);
          testPanel.add(leftPanel);
          testPanel.add(largeView);

        //step1.setBackground(RED);
        //stitchPath.add(path);


    JLabel result;






         int fileCount=0;
         File dir1 = new File(".");
        File folder = new File(dir1.getAbsolutePath()+"\\sample\\Stitched Images\\");
        File[] listOfFiles = folder.listFiles();



        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
               // System.out.println("File " + listOfFiles[i].getName());
                fileCount++;

            }
        }




        String []fileNames = new String[fileCount];
        for(int i = 0; i < fileCount; i++)
        {
            fileNames[i] = listOfFiles[i].getName();
        }




        currentPattern = fileNames[0];

        //Set up the UI for selecting a pattern.
        JLabel patternLabel1 = new JLabel("Select your stitched image");
        JLabel patternLabel2 = new JLabel("select one from the list:");


        for(int i = 0; i < fileCount; i++)
        {
        System.out.println(fileNames[i]);
        }


        JComboBox patternList = new JComboBox(fileNames);
        patternList.setEditable(true);
        //patternList.addActionListener(this);

        //Create the UI for displaying result.
        JLabel resultLabel = new JLabel("Current Date/Time",
                JLabel.LEADING); //== LEFT
        result = new JLabel(" ");
        result.setForeground(Color.black);
        result.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.black),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        //Lay out everything.
        JPanel patternPanel = new JPanel();
        //patternPanel.setLayout(new BoxLayout(patternPanel,
                //BoxLayout.PAGE_AXIS));
        patternPanel.add(patternLabel1);
        patternPanel.add(patternLabel2);
        //patternList.setAlignmentX(Component.LEFT_ALIGNMENT);
        patternPanel.add(patternList);

        JPanel resultPanel = new JPanel(new GridLayout(0, 1));
        resultPanel.add(resultLabel);
        resultPanel.add(result);

       // patternPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
       // resultPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(patternPanel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(resultPanel);

        //setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));










         int fileCount1=0;

        File folder1 = new File(dir1.getAbsolutePath()+"\\src\\Scripts\\Analyzed Images\\");
        File[] listOfFiles1 = folder1.listFiles();

        for (int i = 0; i < listOfFiles1.length; i++) {
            if (listOfFiles1[i].isFile()) {
               // System.out.println("File " + listOfFiles[i].getName());
                fileCount1++;

            } 
        }
        
        
        
       
        String []fileNames1 = new String[fileCount1];
        for(int i = 0; i < fileCount1; i++)
        {
            fileNames1[i] = listOfFiles1[i].getName();
        }
      
        
        

        currentPattern = fileNames1[0];

        //Set up the UI for selecting a pattern.
         stitchViewLabel = new JLabel("Stitched Images");
         stitchViewLabel.setForeground(white);
         
         stitchBackground.add(stitchViewLabel);
        
          analyzedViewLabel = new JLabel("Analyzed Images");
         analyzedViewLabel.setForeground(white);
        
         analyzeBackground.add(analyzedViewLabel);

        
        
        for(int i = 0; i < fileCount1; i++)
        {
        System.out.println(fileNames1[i]);
        }
       

       
        stitchedImageViewer = new JComboBox(fileNames);
        stitchedImageViewer.setEditable(true);
        
        
        analyzedImageViewer = new JComboBox(fileNames1);
        analyzedImageViewer.setEditable(true);
        
        
        
        
        
        
        
        
        
        
        
        

        
    stitchJLabel.setText("Select a stitched map to analyze: ");
    stitchJLabel.setForeground(white);
    
        stitchJLabel.setHorizontalAlignment(JLabel.LEFT);
       
        
        
        input.add(stitchJLabel, JLabel.LEFT_ALIGNMENT);
        patternList.setBorder(compound);
        patternList.setMaximumSize(new Dimension(200, patternList.getMinimumSize().height));
        patternList.setAlignmentX(Component.LEFT_ALIGNMENT);
       
        input.add(patternList);
        //step2.add(patternList);
        
       

   
stitchJLabel2.setText("                                    Enter a name for the output file: (Example: image.jpg)  ");
        stitchJLabel2.setAlignmentX(Component.LEFT_ALIGNMENT);
         stitchJLabel2.setForeground(white);
        
        
       
       
        
        
        input.add(stitchJLabel2, JLabel.LEFT_ALIGNMENT);
        path2.setBorder(compound);
        
        path2.setMaximumSize(new Dimension(200, path2.getMinimumSize().height));
        path2.setAlignmentX(Component.LEFT_ALIGNMENT);
        //path2.setLayout(JTextArea.LEFT_ALIGNMENT);
     
         //path2.setPreferredSize(new Dimension(150,20));
          //insideMacro.setMaximumSize(new Dimension(1000, insideMacro.getMaximumSize().height));
        
        input.add(path2);
         // step2.add(path2);
           
        input.setMaximumSize(new Dimension(2000, (path2.getMinimumSize().height)+20));
        
        
   
       
        
        
        input.setBackground(new Color(72,120,120));
step2.add(input);
         
        
        patternList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
        String newSelection = (String) cb.getSelectedItem();
        currentPattern = newSelection;
        
        System.out.println("Input file selected: "+newSelection);
        pathFile = newSelection;
        
        
            }

        });
        
        
        
        stitchedImageViewer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               
                
                imagePanel.removeAll();
                
                
                JComboBox cb = (JComboBox) e.getSource();
        String newSelection = (String) cb.getSelectedItem();
        currentPattern = newSelection;
        
       
        
             
                
        
        System.out.println("Input file selected: "+newSelection);
        //pathFile2 = newSelection;
         File dir1 = new File ("");
       
         
         String imgDir =dir1.getAbsolutePath()+"\\src\\Scripts\\Stitched Images\\"+newSelection;
        ImageIcon fullSize = new ImageIcon(imgDir);
        
        
        Image img = fullSize.getImage();
        
        Image smallSize = img.getScaledInstance(250,250,java.awt.Image.SCALE_SMOOTH);
        
        fullSize = new ImageIcon(smallSize);
        
        System.out.println("Stitched image directory: "+imgDir);
        stitchedImg = new JLabel(fullSize);
        
        
        //imagePanel.setMaximumSize(new Dimension(1000, 250));
        imagePanel.add(stitchedImg);
        //imagePanel.setBackground(RED);
        uavWindowed.this.revalidate();
        
        
            }

        });
        
         analyzedImageViewer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
        imagePanel2.removeAll();
                
                
                JComboBox cb = (JComboBox) e.getSource();
        String newSelection = (String) cb.getSelectedItem();
        currentPattern = newSelection;
        
        System.out.println("Input file selected: "+newSelection);
        //pathFile2 = newSelection;
         File dir1 = new File ("");
       
         
         String imgDir =dir1.getAbsolutePath()+"\\src\\Scripts\\Analyzed Images\\"+newSelection;
        ImageIcon fullSize = new ImageIcon(imgDir);
        
        
        Image img = fullSize.getImage();
        
        Image smallSize = img.getScaledInstance(250,250,java.awt.Image.SCALE_SMOOTH);
        
        fullSize = new ImageIcon(smallSize);
        
        System.out.println("Analyzed image directory: "+imgDir);
        stitchedImg = new JLabel(fullSize);
        
        
        //imagePanel.setMaximumSize(new Dimension(1000, 250));
        imagePanel2.add(stitchedImg);
        //imagePanel.setBackground(RED);
        uavWindowed.this.revalidate();
        
        
            }

        });
        
        
       
        
          
        
        
   

        
        
        
        
        path2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pathFile2 = path2.getText();
                System.out.println(pathFile2);

                JLabel badPercentage = new JLabel();
//                badPercentage.setText(badPercent);
//                step3.add(badPercentage);

                Analysis();
                
                imageMacro.removeAll();
                
                 if(started == 0)
        {
          
       System.out.println("STARTED IS EQUAL TO 0");
        File dir1 = new File (".");
        
         
         String imgDir =dir1.getAbsolutePath()+"\\src\\Scripts\\Stitched Images\\"+pathFile;
        ImageIcon fullSize = new ImageIcon(imgDir);
        
        
        Image img = fullSize.getImage();
        
        Image smallSize = img.getScaledInstance(250,250,java.awt.Image.SCALE_SMOOTH);
        
        fullSize = new ImageIcon(smallSize);
        
        System.out.println("Stitched image directory: "+imgDir);
        stitchedImg = new JLabel(fullSize);
        
        
        //imagePanel.setMaximumSize(new Dimension(1000, 250));
        imagePanel.removeAll();
        imagePanel.add(stitchedImg);
        //imagePanel.setBackground(RED);
        uavWindowed.this.revalidate();
        
        
        
         System.out.println("STARTED IS EQUAL TO 0");
        File dir2 = new File (".");
        
         
         String imgDir2 =dir2.getAbsolutePath()+"\\src\\Scripts\\Analyzed Images\\"+pathFile2;
        ImageIcon fullSize2 = new ImageIcon(imgDir2);
        
        
        Image img2 = fullSize2.getImage();
        
        Image smallSize2 = img2.getScaledInstance(250,250,java.awt.Image.SCALE_SMOOTH);
        
        fullSize2 = new ImageIcon(smallSize2);
        
        System.out.println("Stitched image directory: "+imgDir);
        stitchedImg = new JLabel(fullSize2);
        
        
        //imagePanel.setMaximumSize(new Dimension(1000, 250));
       imagePanel2.removeAll();
        imagePanel2.add(stitchedImg);
        //imagePanel.setBackground(RED);
        uavWindowed.this.revalidate();
        }
                   badPercentage.setText(badPercent);
//                step3.add(badPercentage);
                //  ImageIcon image = new ImageIcon("C:\\Users\\Majok\\Documents\\UAVStuff\\src\\Scripts\\Analyzed Images\\"+pathFile2);
                //JLabel label = new JLabel(badPercent, image, JLabel.CENTER);
               
                
                 File dir1 = new File (".");
System.out.println("current directory: " + dir1.getAbsolutePath());
 FileInLocation = dir1.getAbsolutePath()+"\\src\\Scripts\\Stitched Images\\"+pathFile;
 FileOutLocation = dir1.getAbsolutePath()+"\\src\\Scripts\\Analyzed Images\\"+pathFile2;
ImageIcon AnalyzedMap = new ImageIcon(FileOutLocation);

                JLabel MapOut = new JLabel(AnalyzedMap);
               // MapOut.setPreferredSize(new Dimension(250, AnalyzedMap.getIconHeight()));

                MapOut.setAlignmentX(Component.CENTER_ALIGNMENT);
                MapOut.setAlignmentY(Component.CENTER_ALIGNMENT);
                
                  JLabel percentLabel = new JLabel(badPercent);
                   JLabel channelLabel = new JLabel(channels);
                    JLabel pixelLabel = new JLabel(totalPixels);
                     JLabel countLabel = new JLabel(modifyCount);
                     
                     percentLabel.setForeground(white);
                     channelLabel.setForeground(white);
                     pixelLabel.setForeground(white);
                     countLabel.setForeground(white);
                     
                JPanel panel = new JPanel(new BorderLayout());
               
                 MapOut.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.black),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
                         
                         
        ));
//                 testPanel.add(info);
//                testPanel.add(MapOut);
                 
                
                 
                 //insideMacro.setAlignmentX(Component.LEFT_ALIGNMENT);
                 //insideMacro.setPreferredSize(new Dimension(1000, 80));
                 //imageMacro.setSize(1000, 110);
                 
                 insideMacro.removeAll();
                
                
                insideMacro.add(channelLabel);
                insideMacro.add(pixelLabel);
                insideMacro.add(countLabel);
                insideMacro.add(percentLabel);
                
               //  imageMacro.add(insideMacro);
               
                
                stitchedImageViewer.setBorder(compound);
                analyzedImageViewer.setBorder(compound);
                
                
                //stitchViewLabel.setBackground(RED);
               //s stitchViewLabel.setBorder(compound);
                
                stitchBackground.setMaximumSize(new Dimension(1000, analyzeBackground.getMinimumSize().height));
                stitchBackground.setBackground(new Color(145,120,120));
                imageMacro.add(stitchBackground);
                
                
                
               // imagePanel.setBackground(GREEN);
                
             
                
                
                
        

  
     //add(scrollPane, BorderLayout.CENTER);

                
                
                 //JLabel image = new JLabel(FileOutLocation);
     //scrollpane = new JScrollPane();
                // imageMacro.add(scrollPane, BorderLayout.CENTER);
                
                
                stitchBackground.setBorder(compound);
                analyzeBackground.setBorder(compound);
                  
                
                stitchedImageViewer.setMaximumSize(new Dimension(1000, stitchedImageViewer.getMinimumSize().height));
                imageMacro.add(stitchedImageViewer,new Dimension(1000, stitchedImageViewer.getMinimumSize().height));
                  
                   imagePanel.setMaximumSize(new Dimension(1000, 250));
                    imageMacro.add(imagePanel);
                
                    
                    //imageMacro.add(analyzedViewLabel);
                    analyzeBackground.setMaximumSize(new Dimension(1000, analyzeBackground.getMinimumSize().height));
                    analyzeBackground.setBackground(new Color(145,120,120));
                    imageMacro.add(analyzeBackground);
               
                    
                    
                  analyzedImageViewer.setMaximumSize(new Dimension(1000, analyzedImageViewer.getMinimumSize().height));
                imageMacro.add(analyzedImageViewer,new Dimension(1000, analyzedImageViewer.getMinimumSize().height));
                
                  
                   imagePanel2.setMaximumSize(new Dimension(1000, 250));
                    imageMacro.add(imagePanel2);
                    
                    
                    JPanel inPanel = new JPanel();
                   
                    //imageMacro.add(insideMacro);
                    
                    inPanel.removeAll();
                    
                    
                    inPanel.remove(insideMacro);
                    inPanel.add(insideMacro);
                    
                   
                    imageMacro.remove(inPanel);
                    imageMacro.add(inPanel);
                    
                    
                    //imageMacro.add(insideMacro);
   
             
                
            
                 
                largeView.removeAll();
                largeView.add(MapOut, JLabel.CENTER_ALIGNMENT);
              
                
                step2.add(testPanel);
               

                // JLabel label = new JLabel(badPercent, image, JLabel.CENTER);
               

                uavWindowed.this.getContentPane().revalidate();


            }

        });

//        stitchJLabel2.setText("Select and enter a name for your output image. (Example field.jpg)");
//        stitchJLabel2.setVerticalAlignment(JLabel.CENTER);
//        step2.add(stitchJLabel2, JLabel.LEFT_ALIGNMENT);
      
        uavWindowed.this.getContentPane().revalidate();
//        ImageIcon finalImage = new ImageIcon(pathFile2);
//
//        JLabel outputImage = new JLabel(finalImage);
//        JPanel analyzedMap = new JPanel();
    }
    
        
        


    

    public void Analysis() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        
         File dir1 = new File (".");
        FileInLocation = dir1.getAbsolutePath()+"\\src\\Scripts\\Stitched Images\\"+pathFile;
 FileOutLocation = dir1.getAbsolutePath()+"\\src\\Scripts\\Analyzed Images\\"+pathFile2;
 
        System.out.println("In analysis, File in: "+FileInLocation);
        //Mat mat = Mat.eye( 3, 3, CvType.CV_8UC1 );
        InputStreamReader converter = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(converter);
        //System.out.println( "mat = " + mat.dump() );
        System.out.println(System.getProperty("user.dir"));
        Mat vineyard;
        String imagepath;

        System.out.println("Enter the exact path of the stiched image: " + FileInLocation);
       
        imagepath = FileInLocation;
        // imagepath = "C:\\Users\\Majok\\Documents\\UAVStuff\\src\\uavstuff\\test_1.jpg";
        vineyard = Highgui.imread(imagepath);

        Mat re_vin = new Mat();
        Size sz = new Size(400, 400);
        Imgproc.resize(vineyard, re_vin, sz);

        Mat gray_vin = new Mat();
        Imgproc.cvtColor(re_vin, gray_vin, Imgproc.COLOR_RGB2GRAY);
        System.out.println("re_vin has " + re_vin.channels() + " channels.");
        channels = "re_vin has " + re_vin.channels() + " channels.";

        double total_pix = re_vin.rows() * re_vin.cols();
        double modify_count = 0;
        System.out.println("Total Pixels: " + total_pix);
        totalPixels = "Total Pixels: " + total_pix;

        for (int i = 0; i < re_vin.rows(); i++) {
            for (int j = 0; j < re_vin.cols(); j++) {
                double[] rgb_values = re_vin.get(i, j);
                double[] yellowfy = {0, 255, 255}; //RGB Values for the color yellow. Needed for coloring a pixel yellow
                double[] whiteify = {255, 255, 255}; // RGB values for the color white. 
                if (rgb_values[0] > 100 && rgb_values[1] > 100 && rgb_values[2] > 100) {
                    re_vin.put(i, j, yellowfy); // // pixel values under 100 are painted yellow for all three values (R,G,B)
                    modify_count++;
                }
                if (rgb_values[0] < 50 && rgb_values[1] < 50 && rgb_values[2] < 50) {
                    re_vin.put(i, j, whiteify);  // pixel values under 50 are painted white for all three values (R,G,B)
                }
            }
        }

        System.out.println("Modify count = " + modify_count);
        modifyCount = "Modify count = " + modify_count;
        
        badPercent = "Bad Crop Percentage is: " + ((modify_count / total_pix) * 100) + "%.";
        System.out.println("Bad Crop Percentage is: " + ((modify_count / total_pix) * 100) + "%.");

        for (int i = 0; i < vineyard.rows(); i++) {
            for (int j = 0; j < vineyard.cols(); j++) {
                double[] rgb_values = vineyard.get(i, j);
                double[] yellowfy = {0, 255, 255};
                double[] whiteify = {255, 255, 255};
                if (rgb_values[0] > 100 && rgb_values[1] > 100 && rgb_values[2] > 100) {
                    vineyard.put(i, j, yellowfy);
                }
                if (rgb_values[0] < 50 && rgb_values[1] < 50 && rgb_values[2] < 50) {
                    vineyard.put(i, j, whiteify);
                }
            }
        }

        System.out.println("Enter the exact path of output image: ");
        //imagepath = "C:\\Users\\Majok\\Documents\\UAVStuff\\src\\Scripts\\Analyzed Images\\" + pathFile2;
        imagepath = FileOutLocation;
        Highgui.imwrite(imagepath, vineyard);

    }

    public static void main(String[] args) throws IOException, InterruptedException {

        String inputFolder = "";
        String outputFolder="";
        String stitchedImage="";
        //In other words, useless stuff. take code from uaveWindowed function that isn't GUI stuff.
        System.out.println("Enter the name of the folder in which the pictures are located");   //Prompting the user to input the name of the folder in which the pictures are located
        Scanner input = new Scanner(System.in);
        inputFolder = input.nextLine();     //Input the name of the folder containing the pictures
        System.out.println("Please enter the name of the folder you want the analysis to be saved to");     //Prompting the user to input the name of the folder in which to save the analyzed images
        outputFolder = input.nextLine();    //Input the name of the folder in which you want to save the analyzed images
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
        imageName.indexOf()
        stitchedImage="%userprofile%\\Documents"+"\\"+listOfFiles[0].getName()+"_stitch.jpg";
        File documentsFolder = new File(stitchedImage);
        File[] listofFilesDocuments = documentsFolder.listFiles();
        while (!documentsFolder.exists())
        {
            Thread.sleep(3000);
            System.out.println(stitchedImage);
        }

        //Run NDVI function with the stitched image with outputFolder
        
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
