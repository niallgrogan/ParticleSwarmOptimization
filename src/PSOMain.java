import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PSOMain implements Constants{

    private static int numRuns = 10;
    public static void main(String[] Args)
    {
//        alphaSweep();
//        betaSweep();
        iterationSweep();
//        runStandardTests();
    }

    private static void meanAndDevTest(int sweepComponent) {
        double[][] results = new double[functions.length][numRuns];
        double[] averages = new double[functions.length];
        for(int j=0; j<functions.length; j++) {
            String function = functions[j];
            for(int i=0; i<numRuns; i++) {
                lBestPSO g = new lBestPSO(function);
                g.initialise(sweepComponent);
                results[j][i] = g.execute()[numIterations-1];
            }
            averages[j] = getAverage(results[j]);
            System.out.println("Function - " + function + "\n" + averages[j]);
        }
        toAlphaCSVFile(sweepComponent,averages);
    }

//    private static void alphaSweep() {
//        for(double alpha :alphaSwings) {
//            meanAndDevTest(alpha);
//        }
//    }
//
//    private static void betaSweep() {
//        for(double beta: betaSwings) {
//            meanAndDevTest(beta);
//        }
//    }

    private static void iterationSweep() {
        for(int iteration:iterationSwings) {
            meanAndDevTest(iteration);
        }
    }

    private static void toAlphaCSVFile(double alpha, double[] averages) {
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(Double.toString(alpha)+"IterationSweep.csv"));
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

            for(int i=0; i<functions.length; i++) {
                sb.append(averages[i]);
                sb.append(", ");
            }
            br.write(sb.toString());
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void runStandardTests() {

        double[] functionMeans = new double[functions.length];
        double[] functionDeviations = new double[functions.length];
        //double[] functionProportions = new double[functions.length];

        int count = 0;
        for (String function :functions) {
            double[][] results = new double[numRuns][numIterations];
            double[] averagedConvData = new double[numIterations];
            double[] finalRow = new double[numRuns];

            for(int j=0; j<numRuns; j++) {
                lBestPSO g = new lBestPSO(function);
                //g.initialise(defaultAlpha);
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

            toConvergenceFile(averagedConvData, function);
            System.out.println("Finished "+function);
            count++;
        }
        toMeanDevFile(functionMeans,functionDeviations);
    }

    private static void toMeanDevFile(double[] means, double[] devs) {
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

    private static double getProportion(double[] data) {
        //TODO: Implement a method that gets the proportion of runs
        //that achieved the goal.
        return 0.0;
    }
}
