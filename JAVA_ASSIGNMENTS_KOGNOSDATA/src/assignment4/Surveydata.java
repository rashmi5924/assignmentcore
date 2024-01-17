package assignment4;

import hdf.hdf5lib.H5;
import hdf.hdf5lib.HDF5Constants;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileReader;

public class Surveydata {

    public static void main(String[] args) {
        try {
            // Step 1: Read the CSV file
            String csvFilePath = "C:\\Users\\RASHMI\\eclipse-workspace\\JAVA_ASSIGNMENTS_KOGNOSDATA\\src\\resources\\Survey_Data.csv";
            CSVParser csvParser = readCSVFile(csvFilePath);

            // Step 2: Create HDF5 file and dataset
            String hdf5FilePath = "Survey.h5";
            createHDF5Dataset(hdf5FilePath, csvParser);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static CSVParser readCSVFile(String csvFilePath) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(csvFilePath));
        CSVParser csvParser = CSVFormat.DEFAULT.withHeader().parse(reader);
        return csvParser;
    }

    private static void createHDF5Dataset(String hdf5FilePath, CSVParser csvParser) throws Exception {
        int fileId = H5.H5Fcreate(hdf5FilePath, HDF5Constants.H5F_ACC_TRUNC, HDF5Constants.H5P_DEFAULT, HDF5Constants.H5P_DEFAULT);

        int numRecords = csvParser.getRecords().size();
        int dataspaceId = H5.H5Screate_simple(1, new long[]{numRecords}, null);

        int datasetId = H5.H5Dcreate(fileId, "DATA", HDF5Constants.H5T_IEEE_F64LE, dataspaceId,
                HDF5Constants.H5P_DEFAULT, HDF5Constants.H5P_DEFAULT, HDF5Constants.H5P_DEFAULT);

        double[] dataArray = new double[numRecords];

        int index = 0;
        for (CSVRecord record : csvParser) {
            double value = Double.parseDouble(record.get("Value"));
            dataArray[index++] = value;
        }

        H5.H5Dwrite(datasetId, HDF5Constants.H5T_NATIVE_DOUBLE, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
                HDF5Constants.H5P_DEFAULT, dataArray);

        // Add attributes to the dataset
        H5.H5DSsetLabel(datasetId, "DATA");
        H5.H5DSsetUnit(datasetId, "Units");
        H5.H5DSsetDataType(datasetId, HDF5Constants.H5T_IEEE_F64LE);

        H5.H5Dclose(datasetId);
        H5.H5Sclose(dataspaceId);
        H5.H5Fclose(fileId);
    }
}

