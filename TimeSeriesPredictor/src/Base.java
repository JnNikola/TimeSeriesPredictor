import java.io.File;
import java.io.FileNotFoundException;


enum TYPE{
    MA,  //moving average
    LP,  //linear predictor
    CLP; //custom linear predictor
}


/**
 * Simulates sync node and measuring node prediction and sending data for a given data set
 * Can use different prediction algorithms like moving average, linear predictor
 * Can sumulate one dimensional and two dimensional data
 * Threshold for prediction vs sending real data is set manually
 *
 * Returns the number of sent and predicted measurements and the average error of predicted data
 * for every given threshold and  prediction algorithm
 *
 */


public class Base {
    public static void main(String[] args) throws FileNotFoundException {

        File skopjeCO= new File("Data/CO.csv");
        double[] karposhCO= CSVParser.readFull(skopjeCO);

        File skopjePM10= new File("Data/PM10.csv");
        double[] karposhPM10= CSVParser.readFull(skopjePM10);

        File EnglandTemp= new File("Data/TS_30-08-2017.csv");
        double[] leicsesterTemp= CSVParser.readSingle(EnglandTemp);

        Node leichesterSingle= new Node(leicsesterTemp);
        Node PM10single= new Node(karposhPM10);
        Node COsingle= new Node(karposhCO);
        Node nodeDouble= new Node(karposhPM10,karposhCO);

        double[] tempTresholds =  {0, 0.15, 0.3, 0.45, 0.6, 0.75, 1, 1.15, 1.5, 1.75, 1.9};
        double[] PM10Tresholds =  {0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}; //{1000, 1000, 1000, 1000 ,1000, 1000 ,1000 , 1000}
        double[] COTresholds =    {0, 0.02, 0.04, 0.06, 0.08, 0.1, 0.12, 0.15, 0.17, 0.2, 0.25, 0.3};//{1000, 1000, 1000, 1000 ,1000, 1000 ,1000 , 1000, 1000, 1000, 1000, 1000} ;

        System.out.println("SINGLE VALUES");
        System.out.println("Temperature:");
        for (TYPE type: TYPE.values()){
            for (double temp: tempTresholds){

                System.out.printf("Predictor: %s, Treshold: %.2f, Sent: %.2f%%, Error: %.5f%%\n", type.toString(), temp, (double)leichesterSingle.countSingle(type, temp)/leicsesterTemp.length*100,
                        MRSerror.normalizedError(leicsesterTemp, leichesterSingle.getPredictedData()));
            }
        }

        System.out.println("PM10:");
        for (TYPE type: TYPE.values()){
            for (double PM10: PM10Tresholds){

                System.out.printf("Predictor: %s, Treshold: %.2f, Sent: %.2f%%, Error: %.5f%%\n", type.toString(), PM10, (double)PM10single.countSingle(type, PM10)/karposhPM10.length*100,
                        MRSerror.normalizedError(karposhPM10, PM10single.getPredictedData()));
            }
        }

        System.out.println("CO:");
        for (TYPE type: TYPE.values()){
            for (double CO: COTresholds){

                System.out.printf("Predictor: %s, Treshold: %.2f, Sent: %.2f%%, Error: %.5f%%\n", type.toString(), CO, (double)COsingle.countSingle(type, CO)/karposhCO.length*100,
                        MRSerror.normalizedError(karposhCO, COsingle.getPredictedData()));
            }
        }


        System.out.println("\nDOUBLE VALUES");
        for (TYPE type: TYPE.values()){
            for (int i=0; i<PM10Tresholds.length; i++){

                System.out.printf("Predictor: %s, Tresholds: %.2fPM10, %.2fCO, Sent: %.2f%%\n", type.toString(), PM10Tresholds[i], COTresholds[i],
                        ((double)nodeDouble.countDouble(type, PM10Tresholds[i], COTresholds[i]))/karposhCO.length*100);
                System.out.printf("PM10 error: %.5f%%, CO error: %.5f%%\n", MRSerror.normalizedError(karposhPM10, nodeDouble.getPredictedData()), MRSerror.normalizedError(karposhCO, nodeDouble.getPredictedData2()));
            }
            System.out.println("");
        }

    }


}
