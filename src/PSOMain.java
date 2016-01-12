import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PSOMain implements Constants{
    public static void main(String[] Args)
    {
        double[][] results = new double[3][numIterations];
        vonNeuPSO von = new vonNeuPSO();
        von.initialise();
        results[0] = von.execute();

        lBestPSO l = new lBestPSO();
        l.initialise();
        results[1] = l.execute();

        gBestPSO g = new gBestPSO();
        g.initialise();
        results[2] = g.execute();

        toCSVFile(results);
    }

    private static void toCSVFile(double[][] arrDouble) {
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter("PSO.csv"));
            StringBuilder sb = new StringBuilder();
            sb.append("Von Neuman");
            sb.append(", ");
            sb.append("lBest");
            sb.append(", ");
            sb.append("gBest");
            sb.append(",\n");

            double[] arr0 = arrDouble[0];
            double[] arr1 = arrDouble[1];
            double[] arr2 = arrDouble[2];
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
