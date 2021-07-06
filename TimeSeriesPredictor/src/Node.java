import java.util.Arrays;

public class Node {

    private final double[] dataSet;
    private double[] predictedData;

    private double[] dataSet2=null;
    private double[] predictedData2 =null;

    public Node(double[] dataSet){
            this.dataSet=dataSet;
            this.predictedData = Arrays.copyOf(dataSet, dataSet.length);

    }

    public Node(double [] dataSet, double[] dataSet2){
        this.dataSet=dataSet;
        this.predictedData = Arrays.copyOf(dataSet, dataSet.length);
        this.dataSet2=dataSet2;
        this.predictedData2 = Arrays.copyOf(dataSet2, dataSet2.length);
    }

    public int countSingle(TYPE type, double treshold){

        int nSent=0;
        this.predictedData = Arrays.copyOf(dataSet, dataSet.length);



        if (type.equals(TYPE.MA)){                //if Moving average
            nSent=2;
            for (int i = 2; i< predictedData.length; i++){

                double prediction = Predictors.MAPredictor(predictedData[i-1], predictedData[i-2]);
                if (Predictors.difference(prediction , predictedData[i])>treshold){
                    nSent++;
                }else predictedData[i]= prediction;
            }
        }


        else if (type.equals(TYPE.LP)){//if Linear predictor
            nSent=2;

            for (int i = 2; i< predictedData.length; i++){

                double prediction = Predictors.LPredictor(predictedData[i-1]);
                if (Predictors.difference(prediction , predictedData[i])>treshold){
                    nSent++;
                }else predictedData[i]= prediction;
            }
        }

        else if(type.equals(TYPE.CLP)){       //if CLP
            nSent=3;

            for (int i = 3; i< predictedData.length; i++){
                double prediction = Predictors.CLPredictor(predictedData[i-1], predictedData[i-2], predictedData[i-3]);
                if (Predictors.difference(prediction, predictedData[i])> treshold){
                    nSent++;
                } else predictedData[i]= prediction;
            }

        }

        return nSent;
    }


    public int countDouble(TYPE type, double treshold1, double treshold2){

        int nSent;
        this.predictedData = Arrays.copyOf(dataSet, dataSet.length);
        this.predictedData2 = Arrays.copyOf(dataSet2, dataSet2.length);

        if (type.equals(TYPE.MA)){                //if Moving average
            nSent=2;
            for (int i = 2; i< predictedData.length; i++){

                double prediction1 = Predictors.MAPredictor(predictedData[i-1], predictedData[i-2]);
                double prediction2 = Predictors.MAPredictor(predictedData2[i-1], predictedData2[i-2]);
                if (Predictors.difference(prediction1 , predictedData[i])>treshold1 || Predictors.difference(prediction2 , predictedData2[i])>treshold2){
                    nSent++;
                }else {
                    predictedData[i]= prediction1;
                    predictedData2[i]= prediction2;
                }
            }
        }


        else if (type.equals(TYPE.LP)){           //if Linear predictor
            nSent=2;

            for (int i = 2; i< predictedData.length; i++){

                double prediction1 = Predictors.LPredictor(predictedData[i-1]);
                double prediction2 = Predictors.LPredictor(predictedData2[i-1]);
                if (Predictors.difference(prediction1 , predictedData[i])>treshold1 || Predictors.difference(prediction2 , predictedData2[i])>treshold2){
                    nSent++;
                }else {
                    predictedData[i]= prediction1;
                    predictedData2[i]= prediction2;
                }
            }
        }

        else { //if custom linear predictor
            nSent=3;

            for (int i = 3; i< predictedData.length; i++){

                double prediction1 = Predictors.CLPredictor(predictedData[i-1], predictedData[i-2], predictedData[i-3]);
                double prediction2 = Predictors.CLPredictor(predictedData2[i-1], predictedData2[i-2], predictedData2[i-3]);
                if (Predictors.difference(prediction1, predictedData[i])> treshold1 || Predictors.difference(prediction2, predictedData2[i])> treshold2){
                    nSent++;
                } else {
                    predictedData[i]= prediction1;
                    predictedData2[i]=prediction2;
                }

            }
        }

        return nSent;

    }

    public double[] getPredictedData() {
        return predictedData;
    }

    public double[] getPredictedData2() {
        return predictedData2;
    }
}
