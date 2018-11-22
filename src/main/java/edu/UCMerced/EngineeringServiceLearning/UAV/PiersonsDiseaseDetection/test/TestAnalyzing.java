package edu.UCMerced.EngineeringServiceLearning.UAV.PiersonsDiseaseDetection.test;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class TestAnalyzing {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        final Mat source;

        try {

            source = Imgcodecs.imread("C:\\Users\\senio\\Pictures\\Saved Pictures\\test.tif");

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Mat greyMat = source;
        Mat ndviMat = source.clone();

        Imgproc.cvtColor(greyMat, greyMat, Imgproc.COLOR_BGR2RGB);
        Imgproc.cvtColor(greyMat, greyMat, Imgproc.COLOR_RGB2GRAY);


        System.out.println(greyMat.channels() + " Channels");

        for (int i = 0; i < ndviMat.rows(); i++) {
            for (int j = 0; j < ndviMat.cols(); j++) {

                double[] data = ndviMat.get(i, j); //Stores element in an array

                double blue = data[0]; // NIR band
                double green = data[1];
                double red = data[2]; // Red band

                //double ndviCalc = ((blue - red) / (blue + red));
                double ndviCalc = ((blue - red) / (blue + red) * 100 + 100); //HAD CHANGED TO

                double greenCalc;
                double redCalc;

                double ndviOutPut = ndviCalc;
                //  System.out.println(ndviOutPut);

                double[] ndviOut = {ndviCalc};
                greyMat.put(i, j, ndviOut); //Puts element back into

            }
        }

        System.out.println(greyMat.channels() + " Channels");
        Imgproc.equalizeHist(greyMat, greyMat);

        Imgproc.applyColorMap(ndviMat, ndviMat, 2);
//        Imgproc.applyColorMap(greyMat, ndviMat, Imgproc.COLORMAP_HOT);

        Imgcodecs.imwrite("C:\\Users\\senio\\Pictures\\Saved Pictures\\test.tif", ndviMat);
    }

}
