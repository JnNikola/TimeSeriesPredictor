import java.util.Arrays;
import java.util.OptionalDouble;
import java.util.stream.IntStream;

public class MRSerror {
    public static double calculateError(double[] real, double[] predicted){
        double sum= IntStream.range(0, real.length)
                .mapToDouble(i -> Math.pow((real[i] - predicted[i]), 2))
                .sum();
        return 1.0/real.length * (sum);
    }

    public static double normalizedError(double[] real, double [] predicted){

        double averageValue= Arrays.stream(real).average().getAsDouble();
        return (calculateError(real, predicted)/averageValue) * 100;
    }
}
