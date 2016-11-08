package edu.UCMerced.EngineeringServiceLearning.UAV.PiersonsDiseaseDetection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
            public void actionPerformed(ActionEvent e) { textPane1.setText("Analyzing");}
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


}
