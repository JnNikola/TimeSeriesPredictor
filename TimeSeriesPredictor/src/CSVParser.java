import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public final class CSVParser {


    /**
     * Custom parser for reading CSV files and converting them to a double array
     */

    public static double [] readFull(File file) throws FileNotFoundException {

        List<Double> values = new LinkedList<>();
        Scanner input= new Scanner(file);
        input.nextLine();       //Skip first line
        while (input.hasNext()){
                double value = Double.parseDouble(input.nextLine().split(",")[3]); //Value for Karposh is at column 4
            values.add(value);
        }
        return values.stream().mapToDouble(Double::doubleValue).toArray();
    }

    public static double [] readSingle(File file) throws FileNotFoundException {


        Scanner input= new Scanner(file);
        String[] values= input.next().split(",");
        return Arrays.stream(values).mapToDouble(Double::parseDouble).toArray();
    }
}
