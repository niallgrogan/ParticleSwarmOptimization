import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PSOMain implements Constants{

    private static int numRuns = 25;
    public static void main(String[] Args)
    {
        runStandardTests();
    }

    private static void runStandardTests() {

        double[] functionMeans = new double[functions.length];
        double[] functionDeviations = new double[functions.length];
        double[] functionProportions = new double[functions.length];

        int count = 0;
        for (String function :functions) {
            double[][] results = new double[numRuns][numIterations];
            double[] averagedConvData = new double[numIterations];
            double[] finalRow = new double[numRuns];

            for(int j=0; j<numRuns; j++) {
                lBestPSO g = new lBestPSO(function);
                g.initialise();
                results[j] = g.execute();
            }

            for(int i=0; i<numIterations; i++) {
                double[] oneRowData = new double[numRuns];
                for(int k=0; k<numRuns; k++) {
                    oneRowData[k] = results[k][i];
                }
                averagedConvData[i] = getAverage(oneRowData);
                finalRow = oneRowData;
            }
            functionMeans[count] = getAverage(finalRow);
            functionDeviations[count] = getStdDev(finalRow, functionMeans[count]);
            functionProportions[count] = getProportion(finalRow, function);

            toConvergenceFile(averagedConvData, function);
            System.out.println("Finished "+function);
            count++;
        }
        toMeanDevFile(functionMeans,functionDeviations, functionProportions);
    }

    private static void toMeanDevFile(double[] means, double[] devs, double[] proportions) {
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy");
            BufferedWriter br = new BufferedWriter(new FileWriter("MeanDevs_"+sdf.format(date)+".csv"));
            StringBuilder sb = new StringBuilder();
            for(String s : functions) {
                sb.append(s);
                if(s.equals("Griewank(10D)")) {
                    sb.append(",\n");
                }
                else {
                    sb.append(", ");
                }
            }
            for(double m:means) {
                sb.append(m);
                sb.append(", ");
            }
            sb.append("\n");
            for(double d:devs) {
                sb.append(d);
                sb.append(", ");
            }
            sb.append("\n");
            for(double p:proportions) {
                sb.append(p);
                sb.append(", ");
            }
            br.write(sb.toString());
            br.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static void toConvergenceFile(double[] averagedConvData, String function) {
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(function+"Convergence_Mean.csv"));
            StringBuilder sb = new StringBuilder();
            for(double d:averagedConvData) {
                sb.append(d);
                sb.append(",\n");
            }
            br.write(sb.toString());
            br.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static double getAverage(double[] arr) {
        double sum =0.0;
        for(double d : arr) sum += d;
        return sum / (double) arr.length;
    }

    private static double getStdDev(double[] data, double mean)
    {
        double temp = 0;
        for(double a :data)
            temp += (mean-a)*(mean-a);
        return Math.sqrt(temp/(double)data.length);
    }

    private static double getProportion(double[] data, String function) {
        double goal;
        double numCorrect = 0.0;
        switch (function) {
            case "Sphere": goal = 0.01;
                break;
            case "Rosenbrock": goal = 100;
                break;
            case "Ackley": goal = 0.01;
                break;
            case "Griewank": goal = 0.05;
                break;
            case "Rastrigin": goal = 100;
                break;
            case "Schaffer(2D)": goal = 0.00001;
                break;
            case "Griewank(10D)": goal = 0.05;
                break;
            default: goal = 0.0;
        }
        for(double d:data) {
            if(d <= goal) {
                numCorrect++;
            }
        }
        return numCorrect/numRuns;
    }
}
