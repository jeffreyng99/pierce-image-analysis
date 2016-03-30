package uavstuff;

import org.opencv.core.*;
import org.opencv.highgui.*;
import org.opencv.imgproc.*;
import org.opencv.contrib.*;

import java.io.*;

public class UAV_NDVI2 {

    public static void main(String[] args) {

        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	    InputStreamReader converter = new InputStreamReader(System.in);
	    BufferedReader in = new BufferedReader(converter);
        System.out.println(System.getProperty("user.dir")); 
        Mat vineyard;
        String imagepath;
      
        try{
        	
	        System.out.println("Enter the exact path of the Red-Near-Infared stiched image: ");
		    imagepath = in.readLine();
	        vineyard = Highgui.imread(imagepath);
	        
	        String filter = "";
	        while(filter.compareToIgnoreCase("red") != 0 && filter.compareToIgnoreCase("blue") != 0){
		        System.out.println("Red Filter or Blue Filter? (red|blue): ");
		        filter = in.readLine();
	        }
	      
	        Mat gray_vin = new Mat();
	        Mat yellow_vin = new Mat();
	        Imgproc.cvtColor(vineyard, gray_vin, Imgproc.COLOR_RGB2GRAY);
	        Imgproc.cvtColor(gray_vin, yellow_vin, Imgproc.COLOR_GRAY2RGB);
	        
	        Mat ndvi = new Mat();
	        ndvi = vineyard;
	        Mat ndvi2 = new Mat();
	        Imgproc.cvtColor(vineyard, ndvi2, Imgproc.COLOR_RGB2GRAY);
	        for(int i=0; i<ndvi.rows(); i++){
	        	for(int j=0; j<ndvi.cols(); j++){
	        		double[] bands = vineyard.get(i, j);
	        		double blue = bands[0];
	        		double green = bands[1];
	        		double red = bands[2];
	        		double nir = 0;
	        		double calc_ndvi = 0;
	        		double thresh = 0;
	        		
		        	////using blue channel as NIR, assuming red filter
		        	////blue = NIR, green = green+NIR, red = red+NIR
		        	if(filter.compareToIgnoreCase("red") == 0){
		        		nir = blue;
		        		red = red - nir;
		        		calc_ndvi = (nir - red)/(nir + red);
		        		thresh = 0.0;
		        	}
		        		
		        	////using red channel as NIR, assuming blue filter
		        	////blue = blue + NIR, green = green+NIR, red = NIR
		        	if(filter.compareToIgnoreCase("blue") == 0){
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
	        			green_ndvi = 255*(1+calc_ndvi);
	        		}
	        		else if(calc_ndvi > 0){
	        			green_ndvi = 255*(1-calc_ndvi);
	        		}
	        		double[] out_ndvi = {blue_ndvi, green_ndvi, red_ndvi};
	        		//double[] out_ndvi = {green, red, nir};
	        		//double[] out_ndvi = {255*(1+calc_ndvi)/2, green, nir};
	        		//double[] out_ndvi = {blue_ndvi, nir, red_ndvi};
	        		ndvi.put(i, j, out_ndvi);
	        		ndvi2.put(i,j, 255*(calc_ndvi+1)/2);
	        		
	        		if(calc_ndvi < thresh){
	        			double[] yellow = {0, 255, 255};
	        			yellow_vin.put(i, j, yellow);
	        		}
	        	}
	        }
	        
	        String imagepath_ndvi = new StringBuilder(imagepath).insert(imagepath.length()-4, "-ndvi").toString();
		    Highgui.imwrite(imagepath_ndvi, ndvi);
		    Contrib.applyColorMap(ndvi2, ndvi2, Contrib.COLORMAP_JET);
		    String imagepath_ndvi2 = new StringBuilder(imagepath).insert(imagepath.length()-4, "-ndvi2").toString();
		    Highgui.imwrite(imagepath_ndvi2, ndvi2);
	        String imagepath_out = new StringBuilder(imagepath).insert(imagepath.length()-4, "-yellow").toString();
		    Highgui.imwrite(imagepath_out, yellow_vin);
		    System.out.println("Done!");

        } catch (IOException e){
	    	System.out.println("Error: Could not open path.");
	    }
    }
}
