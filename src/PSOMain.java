import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PSOMain implements Constants{

    private static int numRuns = 10;
    public static void main(String[] Args)
    {
        double[] results = new double[30];
        alphaSweep();
//        getConvergenceData();
//          getMeanData();
//        for(int i=0; i<1; i++) {
//            lBestPSO von = new lBestPSO();
//            von.initialise();
//            von.execute();
//        }
    }

    private static void alphaSweep() {
        for(double alpha :alphaSwings) {
            double[][] results = new double[functions.length][numRuns];
            double[] averages = new double[functions.length];
            for(int j=0; j<functions.length; j++) {
                String function = functions[j];
                for(int i=0; i<numRuns; i++) {
                    gBestPSO g = new gBestPSO(function);
                    g.initialise(alpha);
                    results[j][i] = g.execute()[numIterations-1];
                }
                averages[j] = getAverage(results[j]);
                System.out.println("Function - " + function + "\n" + averages[j]);
            }
            toAlphaCSVFile(alpha,averages);
        }
    }

    private static void toAlphaCSVFile(double alpha, double[] averages) {
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(Double.toString(alpha)+"AlphaSweep.csv"));
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

//    private static void getMeanData() {
//        for (String function :functions) {
//            double[][] results = new double[3][25];
//            for(int i=0; i<25; i++) {
//                gBestPSO g = new gBestPSO(function);
//                g.initialise();
//                results[0][i] = g.execute()[numIterations-1];
//
//                //Only global topology is used for EMP
////                lBestPSO l = new lBestPSO(function);
////                l.initialise();
////                results[1][i] = l.execute()[9999];
////
////                vonNeuPSO von = new vonNeuPSO(function);
////                von.initialise();
////                results[2][i] = von.execute()[9999];
//
////                System.out.println(i);
//            }
//            double average = getAverage(results[0]);
//            System.out.println("Function - "+function+"\n"+average);
////            toCSVFile(results, function);
////            System.out.println("Finished "+function);
//        }
//    }

//    private static void getConvergenceData() {
//        for (String function :functions) {
//            double[][] results = new double[3][numIterations];
//
//            gBestPSO g = new gBestPSO(function);
//            g.initialise();
//            results[0] = g.execute();
//
////            lBestPSO l = new lBestPSO(function);
////            l.initialise();
////            results[1] = l.execute();
////
////            vonNeuPSO von = new vonNeuPSO(function);
////            von.initialise();
////            results[2] = von.execute();
//
//            toCSVFile(results, function);
//            System.out.println("Finished "+function);
//        }
//    }


    private static void toCSVFile(double[][] arrDouble, String function) {
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(function+"Convergence.csv"));
            StringBuilder sb = new StringBuilder();
            sb.append("gBest");
            sb.append(", ");
            sb.append("lBest");
            sb.append(", ");
            sb.append("Von Neumann");
            sb.append(",\n");

            double[] arr0 = arrDouble[0];
            double[] arr1 = arrDouble[1];
            double[] arr2 = arrDouble[2];
            //Change when getting convergence data
            for(int i=0; i<1; i++)
            {
                sb.append(arr0[i]);
                sb.append(", ");
                sb.append(arr1[i]);
                sb.append(", ");
                sb.append(arr2[i]);
                sb.append(",\n");
            }
            br.write(sb.toString());
            br.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static double getAverage(double[] arr) {
        double sum =0.0;
        for(double d : arr) sum += d;
        return sum / (double) arr.length;
    }
}
