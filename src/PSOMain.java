import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PSOMain implements Constants{
    public static void main(String[] Args)
    {
        double[] results = new double[30];
        getConvergenceData();
//          getMeanData();
//        for(int i=0; i<1; i++) {
//            lBestPSO von = new lBestPSO();
//            von.initialise();
//            von.execute();
//        }
    }

    private static void getMeanData() {
        for (String function :functions) {
            double[][] results = new double[3][25];
            for(int i=0; i<25; i++) {
                gBestPSO g = new gBestPSO(function);
                g.initialise();
                results[0][i] = g.execute()[9999];

                lBestPSO l = new lBestPSO(function);
                l.initialise();
                results[1][i] = l.execute()[9999];

                vonNeuPSO von = new vonNeuPSO(function);
                von.initialise();
                results[2][i] = von.execute()[9999];

                System.out.println(i);
            }
            toCSVFile(results, function);
            System.out.println("Finished "+function);
        }
    }

    private static void getConvergenceData() {
        for (String function :functions) {
            double[][] results = new double[3][numIterations];

            gBestPSO g = new gBestPSO(function);
            g.initialise();
            results[0] = g.execute();

            lBestPSO l = new lBestPSO(function);
            l.initialise();
            results[1] = l.execute();

            vonNeuPSO von = new vonNeuPSO(function);
            von.initialise();
            results[2] = von.execute();

            toCSVFile(results, function);
            System.out.println("Finished "+function);
        }
    }


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
            for(int i=0; i<numIterations; i++)
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
}
