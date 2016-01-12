import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PSOMain {
    public static void main(String[] Args)
    {
        vonNeuPSO p = new vonNeuPSO();
        p.initialise();
        double[] results = p.execute();
        toCSVFile(results);
    }

    private static void toCSVFile(double[] arr) {
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter("pso.csv"));
            StringBuilder sb = new StringBuilder();
            for (double el : arr) {
                sb.append(el);
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
