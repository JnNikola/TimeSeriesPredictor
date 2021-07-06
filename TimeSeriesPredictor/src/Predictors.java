public final class Predictors {

    public static double MAPredictor(double n, double m){        //moving average order 2
        return (n+m)/2;
    }

    public static double LPredictor(double n){         //linear predictor first order
        return n;
    }

    public static double CLPredictor(double n, double m, double l){     //custom linear predictor
        return 0.5*n + 0.3*m + 0.2*l;
    }

    public static double difference(double n, double m){
        return Math.abs(n-m);
    }

}


