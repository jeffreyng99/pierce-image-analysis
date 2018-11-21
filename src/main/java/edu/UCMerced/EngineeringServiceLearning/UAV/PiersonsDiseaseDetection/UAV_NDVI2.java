package edu.UCMerced.EngineeringServiceLearning.UAV.PiersonsDiseaseDetection;

import org.opencv.contrib.Contrib.*;
import org.opencv.core.*;
import org.opencv.highgui.*;
import org.opencv.imgproc.*;
import org.opencv.contrib.*;
//import org.opencv.imgcodecs.Imgcodecs;
//import org.opencv.imgproc.Imgproc;

import java.io.*;

public class UAV_NDVI2 {

	public double[] assignColor(double value) {
		double[] bgr = new double[3];
		if (value < .30D) { bgr[0] = 0; bgr[1] = 0; bgr[2] = 0; }
		else if (value >= .30D && value < .35D) { bgr[0] = 41; bgr[1] = 29; bgr[2] = 165; }
		else if (value >= .35D && value < .40D) { bgr[0] = 39; bgr[1] = 46; bgr[2] = 217; }
		else if (value >= .40D && value < .45D) { bgr[0] = 68; bgr[1] = 109; bgr[2] = 242; }
		else if (value >= .45D && value < .50D) { bgr[0] = 98; bgr[1] = 174; bgr[2] = 250; }
		else if (value >= .50D && value < .55D) { bgr[0] = 192; bgr[1] = 247; bgr[2] = 250; }
		else if (value >= .55D && value < .60D) { bgr[0] = 141; bgr[1] = 230; bgr[2] = 220; }
		else if (value >= .60D && value < .65D) { bgr[0] = 106; bgr[1] = 211; bgr[2] = 106; }
		else if (value >= .65D && value < .70D) { bgr[0] = 72; bgr[1] = 183; bgr[2] = 77; }
		else if (value >= .70D && value < .75D) { bgr[0] = 75; bgr[1] = 179; bgr[2] = 42; }
		else if (value >= .75D && value < .80D) { bgr[0] = 80; bgr[1] = 152; bgr[2] = 28; }
		else if (value >= .80D && value < .85D) { bgr[0] = 76; bgr[1] = 139; bgr[2] = 13; }
		else if (value >= .85D && value < .90D) { bgr[0] = 57; bgr[1] = 104; bgr[2] = 18; }
		else if (value >= .90D && value < 1D) { bgr[0] = 255; bgr[1] = 255; bgr[2] = 255; }			

		return bgr;
	}

	public double[] assignColor(double value, double max, double min, double partition) {
		double one, two, three, four;
		double hmm = (max - min) / (partition);
		one = hmm; two = 2D * hmm; three = 3D * hmm; four = 4D * hmm;
		double[] bgr = new double[3];
		if (value < one) { bgr[0] = 28; bgr[1] = 25; bgr[2] = 215; }							//red
		else if (value >= one && value < two) { bgr[0] = 97; bgr[1] = 174; bgr[2] = 253; }		//orange
		else if (value >= two && value < three) { bgr[0] = 192; bgr[1] = 255; bgr[2] = 255; }		//pale
		else if (value >= three && value < four) { bgr[0] = 106; bgr[1] = 2117; bgr[2] = 166; }	//light green
		else if (value >= four && value < max) { bgr[0] = 65; bgr[1] = 150; bgr[2] = 26; }   	//green
		return bgr;
	}

	public Mat NDVIProcessing(String inputImagePath, boolean isRedFilter) {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME); //this is needed to remove an unsatisfied link error
		System.out.println(System.getProperty("user.dir"));
		Mat inputImageMatrix;

		try{


			inputImageMatrix = Highgui.imread(inputImagePath);

		} catch (Exception e){
			return null;
		}

			Mat ndvi = new Mat();                                //Creates a matrix
			ndvi = inputImageMatrix;                             //Names ndvi as inputImageMatrix
			Mat greyMat = inputImageMatrix.clone();
			Imgproc.cvtColor(greyMat, greyMat, Imgproc.COLOR_BGR2GRAY);
			System.out.println("Hey");
			double high = 0D;
			double low = 50D;
			// Chris, change this if you're going to add more colors
			double one, two, three, partition;
			// Add more colors if you're going to make a better gradient

			for(int i=0; i<ndvi.rows(); i++){ 
				System.out.print(i + " ");							                   //loops through each pixel
				for(int j=0; j<ndvi.cols(); j++){                //loops through each pixel
					double[] bands = inputImageMatrix.get(i, j); //returns the value of blue, green & red for each index respectively
					double blue = bands[0];                      //double is the data type of variable, it takes values that have large decimals
					double green = bands[1];                     //blue, green, red, nir, calc_ndvi and thresh are the names of the of the variables
					double red = bands[2];                       //bands[],0, and imputImageMatrix.get => are the values of the variables repectively
					double nir = 0;
					double calc_ndvi = 0;
					double thresh = 0;
					

					////using blue channel as NIR, assuming red filter
					////blue = NIR, green = green+NIR, red = red+NIR
					if(isRedFilter){
						nir = blue;
						//red = red - nir;   //wtf is this doing here.
						calc_ndvi = ((nir - red)/(nir + red));
						calc_ndvi = calc_ndvi + 1.0D;
						calc_ndvi = (calc_ndvi * 255.0D) / (2.0D);
						if(calc_ndvi < low) {
							low = calc_ndvi;
						}
						if(calc_ndvi > high) {
							high = calc_ndvi;
						}
						double[] ndviOut = {calc_ndvi};
						//calc_ndvi = calc_ndvi * 10D; this is what I added
						//System.out.println(calc_ndvi);
						thresh = 0.8;
						greyMat.put(i,j,ndviOut);
					}

					////using red channel as NIR, assuming blue filter
					////blue = blue + NIR, green = green+NIR, red = NIR
					
					//double[] out_ndvi = {blue_ndvi, red_ndvi, green_ndvi};      //original blue green red
					//double[] out_ndvi = {green, red, nir};
					//double[] out_ndvi = {255*(1+calc_ndvi)/2, green, nir};
					//double[] out_ndvi = {blue_ndvi, nir, red_ndvi};
					//ndvi.put(i, j, out_ndvi); //coloring pixel to NDVI scale based on calculation
					//Jeffrey del double[] out_ndvi = assignColor(calc_ndvi); 
					//Jeffrey del ndvi.put(i, j, out_ndvi); //coloring pixel to NDVI scale based on calculation
				}
			}
			System.out.println(high);
			System.out.println(low);
			System.out.println("done");
			//Mat dist;
			Imgproc.equalizeHist(greyMat, greyMat);
			high = 50;
			low = 50;

			for(int i=0; i<ndvi.rows(); i++){ 
				System.out.print(i + " ");							                   //loops through each pixel
				for(int j=0; j<ndvi.cols(); j++){
						double[] calc_ndvi = greyMat.get(i, j); 
						if(calc_ndvi[0] < low) {
							low = calc_ndvi[0];
						}
						if(calc_ndvi[0] > high) {
							high = calc_ndvi[0];
						}
				}
			} 
			System.out.println(high);
			System.out.println(low);
			System.out.println("done");

			   //Contrib.applyColorMap(greyMat, greyMat, 3);
			Mat clone = ndvi.clone();

			for(int i = 0; i < greyMat.rows(); i++) {
				for(int j = 0; j < greyMat.cols(); j++) {
					double[] bands = greyMat.get(i, j);
					double val = bands[0];
					clone.put(i, j, assignColor(val, high, low, 5D));
				}
			}
			//Imgproc.cvtColor(inputImageMatrix, ndvi, Imgproc.COLOR_BGR2GRAY);
			return clone;


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

	/*public Mat dualNDVIProcessing(String NIRInputImagePath, String secondInputImagePath, boolean isRedFilter) {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME); //this is needed to remove an unsatisfied link error
		System.out.println(System.getProperty("user.dir"));
		Mat firstInputImageMatrix;
		Mat secondInputImageMatrix;

		try{
			firstInputImageMatrix = Highgui.imread(firstInputImagePath);
		} catch (Exception e) {
			return null;
		}
		try{
			secondInputImageMatrix = Highgui.imread(secondInputImagePath);
		} catch (Exception e) {
			return null;
		}

	}*/

}
