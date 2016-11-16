package edu.UCMerced.EngineeringServiceLearning.UAV.PiersonsDiseaseDetection;

import org.opencv.core.*;
import org.opencv.highgui.*;
import org.opencv.imgproc.*;
import org.opencv.contrib.*;

import java.io.*;

public class UAV_NDVI2 {

	public Mat NDVIProcessing(String inputImagePath,boolean isRedFilter) {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME); //this is needed to remove an unsatisfied link error
		System.out.println(System.getProperty("user.dir"));
		Mat inputImageMatrix;

		try{


			inputImageMatrix = Highgui.imread(inputImagePath);

		} catch (Exception e){
			return null;
		}

			Mat gray_vin = new Mat();
			Mat yellow_vin = new Mat();
			Imgproc.cvtColor(inputImageMatrix, gray_vin, Imgproc.COLOR_RGB2GRAY);
			Imgproc.cvtColor(gray_vin, yellow_vin, Imgproc.COLOR_GRAY2RGB);

			Mat ndvi = new Mat();
			ndvi = inputImageMatrix;
			Mat ndvi2 = new Mat();
			Imgproc.cvtColor(inputImageMatrix, ndvi2, Imgproc.COLOR_RGB2GRAY);
			for(int i=0; i<ndvi.rows(); i++){ //loops through each pixel
				for(int j=0; j<ndvi.cols(); j++){
					double[] bands = inputImageMatrix.get(i, j); //returns the value of blue, green & red for each index respectively
					double blue = bands[0];
					double green = bands[1];
					double red = bands[2];
					double nir = 0;
					double calc_ndvi = 0;
					double thresh = 0;

					////using blue channel as NIR, assuming red filter
					////blue = NIR, green = green+NIR, red = red+NIR
					if(isRedFilter){
						nir = blue;
						red = red - nir;
						calc_ndvi = (nir - red)/(nir + red);
						thresh = 0.8;
					}

					////using red channel as NIR, assuming blue filter
					////blue = blue + NIR, green = green+NIR, red = NIR
					else{
						nir = red;
						blue = blue - red;
						calc_ndvi = (nir - blue)/(nir + blue);
						thresh = 0.8;
					}

					double blue_ndvi = -255*calc_ndvi;
					double green_ndvi = 0;
					double red_ndvi = 255*calc_ndvi;
					if(calc_ndvi == 0){
						green_ndvi = 255;
					}
					else if(calc_ndvi < 0){
						green_ndvi = 255*(1+calc_ndvi);//positive value 
					}
					else if(calc_ndvi > 0){
						green_ndvi = 255*(1-calc_ndvi);//negative value 
					}
					double[] out_ndvi = {blue_ndvi, red_ndvi, green_ndvi};      //original blue green red
					//double[] out_ndvi = {green, red, nir};
					//double[] out_ndvi = {255*(1+calc_ndvi)/2, green, nir};
					//double[] out_ndvi = {blue_ndvi, nir, red_ndvi};
					ndvi.put(i, j, out_ndvi); //coloring pixel to NDVI scale based on calculation
					ndvi2.put(i,j, 255*(calc_ndvi+1)/2);

					if(calc_ndvi < thresh){
						double[] yellow = {0, 255, 255};
						yellow_vin.put(i, j, yellow);
					}
				}
			}
			return ndvi;
			/*String imagepath_ndvi = new StringBuilder(imagepath).insert(imagepath.length()-4, "-ndvi").toString();
			Highgui.imwrite(imagepath_ndvi, ndvi);
			Contrib.applyColorMap(ndvi2, ndvi2, Contrib.COLORMAP_JET);
			String imagepath_ndvi2 = new StringBuilder(imagepath).insert(imagepath.length()-4, "-ndvi2").toString();
			Highgui.imwrite(imagepath_ndvi2, ndvi2);
			String imagepath_out = new StringBuilder(imagepath).insert(imagepath.length()-4, "-yellow").toString();
			Highgui.imwrite(imagepath_out, yellow_vin);
			System.out.println("Done!");
			*/


	}
}
