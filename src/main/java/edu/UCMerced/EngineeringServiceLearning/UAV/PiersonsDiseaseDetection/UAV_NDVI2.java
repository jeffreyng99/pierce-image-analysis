package edu.UCMerced.EngineeringServiceLearning.UAV.PiersonsDiseaseDetection;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class UAV_NDVI2 extends Thread {

    public Mat NDVIProcessing(String inputImagePath, final boolean isRedFilter) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); //this is needed to remove an unsatisfied link error
        System.out.println(System.getProperty("user.dir"));
        final Mat inputImageMatrix;

        try {

            inputImageMatrix = Imgcodecs.imread(inputImagePath);

        } catch (Exception e) {
            return null;
        }
        Mat gray_vin = new Mat();                           //Creates a matrix
        Mat yellow_vin = new Mat();                         //Creates a matrix
        Imgproc.cvtColor(inputImageMatrix, gray_vin, Imgproc.COLOR_RGB2GRAY);
        Imgproc.cvtColor(gray_vin, yellow_vin, Imgproc.COLOR_GRAY2RGB);

        Mat ndvi = new Mat();                                //Creates a matrix
        ndvi = inputImageMatrix;                             //Names ndvi as inputImageMatrix
        Mat ndvi2 = new Mat();                               //Creates a matrix
        Imgproc.cvtColor(inputImageMatrix, ndvi2, Imgproc.COLOR_RGB2GRAY);

        for (int i = 0; i < ndvi.rows(); i++) {                    //loops through each pixel
            for (int j = 0; j < ndvi.cols(); j++) {                //loops through each pixel
                double[] bands = inputImageMatrix.get(i, j); //returns the value of blue, green & red for each index respectively
                double blue = bands[0];                      //double is the data type of variable, it takes values that have large decimals
                double red = bands[2];                       //bands[],0, and imputImageMatrix.get => are the values of the variables repectively
                double nir = 0;
                double calc_ndvi = 0;
                double thresh = 0;

                ////using blue channel as NIR, assuming red filter
                ////blue = NIR, green = green+NIR, red = red+NIR
                if (isRedFilter) {
                    nir = blue;
                    red = red - nir;
                    calc_ndvi = ((blue - red) / (blue + red)) * 100 + 100;
                    thresh = 0.8;
                }

                double blue_ndvi = -180 * calc_ndvi;
                double green_ndvi = 0;
                double red_ndvi = 180 * calc_ndvi;
                if (calc_ndvi == 0) {      //conditon true if green_ndvi = 225
                    green_ndvi = 255;
                } else if (calc_ndvi < 0) {
                    green_ndvi = 255 * (1 + calc_ndvi);   //positive value
                } else if (calc_ndvi > 0) {
                    green_ndvi = 255 * (1 - calc_ndvi);   //negative value
                }
                double[] out_ndvi = {blue_ndvi, red_ndvi, green_ndvi};      //original blue green red
                ndvi.put(i, j, out_ndvi); //coloring pixel to NDVI scale based on calculation
                ndvi2.put(i, j, 255 * (calc_ndvi + 1) / 2);

                if (calc_ndvi < thresh) {
                    double[] yellow = {0, 255, 255};
                    yellow_vin.put(i, j, yellow);
                }
            }
        }
        return ndvi;
    }
}
