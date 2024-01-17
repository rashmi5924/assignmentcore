package assignment3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVParser;

import ncsa.hdf.hdf5lib.H5;
import ncsa.hdf.hdf5lib.HDF5Constants;
import ncsa.hdf.object.h5.H5File;
import ncsa.hdf.object.h5.H5ScalarDS;

public class MudWeightMultiplier{
	

    public static void main(String[] args) {
        try {
        	String filename="C:\\Users\\RASHMI\\eclipse-workspace\\JAVA_ASSIGNMENTS_KOGNOSDATA\\src\\resources\\Mud_Weight.csv";
            // Step 1: Read Mud_Weight.csv file
            List<Float> mudWeights = readCsvFile(filename);

            // Step 2: Multiply Mud_Weight data with Math.PI
            List<Float> mudWeightsPI = multiplyWithPI(mudWeights);

            // Step 3: Create Mud_Weight_PI.csv file with the output
            writeCsvFile("Mud_Weight_PI.csv", mudWeightsPI);

            // Step 4: Create HDF file and write data
            createHDFFile("Mud_Weight_PI.h5", mudWeightsPI);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Float> readCsvFile(String fileName) throws IOException {
        List<Float> mudWeights = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
            for (CSVRecord record : csvParser) {
                float mudWeight = Float.parseFloat(record.get("Mud_Weight"));
                mudWeights.add(mudWeight);
            }
        }

        return mudWeights;
    }

    private static List<Float> multiplyWithPI(List<Float> mudWeights) {
        List<Float> mudWeightsPI = new ArrayList<>();
        for (float mudWeight : mudWeights) {
            mudWeightsPI.add(mudWeight * (float) Math.PI);
        }
        return mudWeightsPI;
    }

    private static void writeCsvFile(String fileName, List<Float> data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Mud_Weight_PI\n");
            for (Float value : data) {
                writer.write(value.toString() + "\n");
            }
        }
    }

    private static void createHDFFile(String fileName, List<Float> data) {
        try {
        	int fileId = H5.H5Fcreate(fileName, HDF5Constants.H5F_ACC_TRUNC, HDF5Constants.H5P_DEFAULT, HDF5Constants.H5P_DEFAULT);

            int dataspaceId = H5.H5Screate_simple(1, new long[]{data.size()}, null);

            int datasetId = H5.H5Dcreate(fileId, "DATA", HDF5Constants.H5T_IEEE_F32BE, dataspaceId,
                    HDF5Constants.H5P_DEFAULT);

            float[] dataArray = new float[data.size()];
            for (int i = 0; i < data.size(); i++) {
                dataArray[i] = data.get(i);
            }

            H5.H5Dwrite(datasetId, HDF5Constants.H5T_NATIVE_FLOAT, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
                    HDF5Constants.H5P_DEFAULT, dataArray);

            // Add attributes to the dataset
            //H5.H5DSsetLabel(datasetId, "Mud_Weight_PI");
            //H5.H5DSsetUnit(datasetId, "Units");

            H5.H5Dclose(datasetId);
            H5.H5Sclose(dataspaceId);
            H5.H5Fclose(fileId);
        	/*
            int fileId = H5.H5Fcreate(fileName, HDF5Constants.H5F_ACC_TRUNC, HDF5Constants.H5P_DEFAULT, HDF5Constants.H5P_DEFAULT);

            int dataspaceId = H5.H5Screate_simple(1, new long[]{data.size()}, null);

            int datasetId = H5.H5Dcreate(fileId, "DATA", HDF5Constants.H5T_IEEE_F32BE, dataspaceId,
                    HDF5Constants.H5P_DEFAULT);
            float[] dataArray = new float[data.size()];
            for (int i = 0; i < data.size(); i++) {
                dataArray[i] = data.get(i);
            }

            
            H5.H5Dwrite(datasetId, HDF5Constants.H5T_NATIVE_FLOAT, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
                    HDF5Constants.H5P_DEFAULT, dataArray);

            // Add properties to the dataset
            H5.H5DSset_scale(datasetId, "Mud_Weight_PI");

            // Add label and unit attributes
            String labelAttribute = "Mud_Weight_PI";
            String unitAttribute = "Units";

            H5.H5DSsetLabel(datasetId, labelAttribute);
            H5.H5DSsetUnit(datasetId, unitAttribute);

            H5.H5Dclose(datasetId);
            H5.H5Sclose(dataspaceId);
            H5.H5Fclose(fileId);*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
    public static void main(String[] args) {
        // Step 1: Read "Mud_Weight.csv" file
        List<Double> mudWeightList = readCsvFile("C:\\Users\\RASHMI\\eclipse-workspace\\JAVA_ASSIGNMENTS_KOGNOSDATA\\src\\resources\\Mud_Weight.csv");

        // Step 2: Multiply "Mud_Weight" data with Math.PI
        List<Double> multipliedList = multiplyWithPI(mudWeightList);

        // Step 3: Create "Mud_Weight_PI.csv" with the output
        writeCsvFile("Mud_Weight_PI.csv", multipliedList);

        // Step 4: Create HDF file and write data from "Mud_Weight_PI.csv"
        //createHDFFile();
        createHDFFile("Mud_Weight_PI.csv", "DATA");

        System.out.println("Processing completed successfully.");
    }

   

	private static List<Double> readCsvFile(String filePath) {
        List<Double> mudWeightList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                double mudWeight = Double.parseDouble(values[0].trim());
                mudWeightList.add(mudWeight);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mudWeightList;
    }

    private static List<Double> multiplyWithPI(List<Double> mudWeightList) {
        List<Double> multipliedList = new ArrayList<>();

        for (Double mudWeight : mudWeightList) {
            double multipliedValue = mudWeight * Math.PI;
            multipliedList.add(multipliedValue);
        }

        return multipliedList;
    }

    private static void writeCsvFile(String filePath, List<Double> data) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Double value : data) {
                writer.write(value + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void createHDFFile(String csvFilePath, String datasetName) {
    	
        try {
        	//System.setProperty("java.library.path", "C:\\Users\\RASHMI\\eclipse-workspace\\JAVA_ASSIGNMENTS_KOGNOSDATA\\src\\lib\\hdf5-1.14.3-Std-centos8_64.tar.gz");
        	//System.loadLibrary("jhdf5");
            H5File file = new H5File("output.h5", H5File.CREATE);

            List<Double> data = readCsvFile(csvFilePath);
            float[] dataArray = new float[data.size()];
            for (int i = 0; i < data.size(); i++) {
                dataArray[i] = data.get(i).floatValue();
            }

            // Create dataset
            H5ScalarDS dataset = new H5ScalarDS(file, datasetName, null);
            dataset.write(dataArray);

            // Add properties to the dataset
            dataset.writeMetadata("Units");

            // Close the file
            file.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
