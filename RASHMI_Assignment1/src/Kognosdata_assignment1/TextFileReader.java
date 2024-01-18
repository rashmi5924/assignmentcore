package Kognosdata_assignment1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class TextFileReader {
	public static void main(String[] args) throws IOException {
		FileReader fr = new FileReader("C:\\Users\\RASHMI\\eclipse-workspace\\RASHMI_Assignment1\\src\\Kognosdata_assignment1\\TestData1.txt");
	BufferedReader br = new BufferedReader(fr);
        String line;
        int sum=0;
        while ((line = br.readLine()) != null) {
        	if(sum>2) {
            // Split the line into fields based on the CSV delimiter (comma)
            String[] fields = line.split("\\s");
            
            // Access each field as needed
            String depth = fields[0];
            String mudWeight = fields[1];
            String mudWeight2 = fields[2];
            // Process the data as needed (e.g., convert to other units, etc.)
            System.out.println(depth + " " + mudWeight+ " " + mudWeight2);
        	}
        	else {
        		sum++;
        	}
        }
    
	
		/*FileReader fr = new FileReader("C:\\Users\\RASHMI\\eclipse-workspace\\RASHMI_Assignment1\\src\\Kognosdata_assignment1\\TestData1.txt");
		BufferedReader bf=new BufferedReader(fr);
		String st=bf.readLine();
		int sum=0;
		while((st=bf.readLine())!=null) {
			if(sum>2) {
			StringTokenizer stn=new StringTokenizer(st);
			//String en=stn.nextToken();
			float phy=Integer.parseInt(stn.nextToken());
			float chem=Integer.parseInt(stn.nextToken());
			float bio=Integer.parseInt(stn.nextToken());
			System.out.println(chem);
			}
			else {
			sum++;
			}
		}
	}*/
	}
}
