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

        String[] tests = {"lBest"};//{"gBest","lBest","vonNeu"};
        for(String t:tests) {
            Double[] functionMeans = new Double[functions.length];
            Double[] functionDeviations = new Double[functions.length];
            Double[] functionProportions = new Double[functions.length];
            int ParticleAdded1 = 0;
            int ParticleAdded2 = 0;
            int count = 0;

            for (int function :functions) {
                Double[][] results = new Double[numRuns][numIterations];
                Double[][] results1 = new Double[numRuns][numIterations];
                Double[][] results2 = new Double[numRuns][numIterations];
                Double[] averagedConvData = new Double[numIterations];
                Double[] finalRow = new Double[numRuns];
                PSOProcess swarm1;
                PSOProcess swarm2;
                int s1size = 0;
                int s2size = 0;

                for(int j=0; j<numRuns; j++) {

                    if(t.equals("gBest")) {
                        swarm1 = new gBestPSO(function);
                        swarm1.initialise();
                        swarm2 = new gBestPSO(function);
                        swarm2.initialise();
                    }
                    else if(t.equals("lBest")) {
                        swarm1 = new lBestPSO(function);
                        swarm1.initialise();
                        swarm2 = new lBestPSO(function);
                        swarm2.initialise();
                    }
                    else {
                        swarm1 = new vonNeuPSO(function);
                        swarm1.initialise();
                        swarm2 = new vonNeuPSO(function);
                        swarm2.initialise();
                    }

                    for(int i=0; i<numIterations; i++) {

                        ParticleAdded1 = swarm1.execute(i);
                        if(ParticleAdded1 > 0) {
                            swarm2.removeWorstParticle(ParticleAdded1);
                        }
                        ParticleAdded2 = swarm2.execute(i);
                        if(ParticleAdded2 > 0) {
                            swarm1.removeWorstParticle(ParticleAdded2);
                        }
                        System.out.println("Swarm 1: "+swarm1.swarm.size()+"  Swarm 2: "+swarm2.swarm.size());
                    }
                    System.out.println("***");
                    results1[j] = swarm1.globalFitnessArray;
                    results2[j] = swarm2.globalFitnessArray;
                    System.out.println("Swarm 1: "+swarm1.swarm.size()+"  Swarm 2: "+swarm2.swarm.size());
                    if(results1[j][numIterations-1] >= results2[j][numIterations-1]) {
                        results[j] = results2[j];
                    }
                    else {
                        results[j] = results1[j];
                    }
                }

                for(int i=0; i<numIterations; i++) {
                    Double[] oneRowData = new Double[numRuns];
                    for(int k=0; k<numRuns; k++) {
                        oneRowData[k] = results[k][i];
                    }
                    averagedConvData[i] = getAverage(oneRowData);
                    finalRow = oneRowData;
                }
                toDataFile(finalRow, function,t);
                functionMeans[count] = getAverage(finalRow);
                functionDeviations[count] = getStdDev(finalRow, functionMeans[count]);
                functionProportions[count] = getProportion(finalRow, function);

                toConvergenceFile(averagedConvData, function,t);
                System.out.println("Finished "+function+" "+t);
                count++;
            }
            toMeanDevFile(functionMeans,functionDeviations, functionProportions,t);
        }
    }

    private static void toDataFile(Double[] results, int function, String test) {
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(test+"_Results_"+function+".csv"));
            StringBuilder sb = new StringBuilder();
            for(double r:results) {
                sb.append(r);
                sb.append(",\n");
            }
            br.write(sb.toString());
            br.close();
        }
        catch (Exception e) {}
    }

    private static void toMeanDevFile(Double[] means, Double[] devs, Double[] proportions, String test) {
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy");
            BufferedWriter br = new BufferedWriter(new FileWriter(test+" MeanDevs_"+sdf.format(date)+".csv"));
            StringBuilder sb = new StringBuilder();
            for(int s : functions) {
                sb.append(s);
                if(s == 32) {
                    sb.append(",\n");
                }
                else {
                    sb.append(", ");
                }
            }
            for(Double m:means) {
                sb.append(m);
                sb.append(", ");
            }
            sb.append("\n");
            for(Double d:devs) {
                sb.append(d);
                sb.append(", ");
            }
            sb.append("\n");
            for(Double p:proportions) {
                sb.append(p);
                sb.append(", ");
            }
            br.write(sb.toString());
            br.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static void toConvergenceFile(Double[] averagedConvData, int function, String test) {
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(test+"_Convergence_"+function+".csv"));
            StringBuilder sb = new StringBuilder();
            for(Double d:averagedConvData) {
                sb.append(d);
                sb.append(",\n");
            }
            br.write(sb.toString());
            br.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static Double getAverage(Double[] arr) {
        Double sum =0.0;
        for(Double d : arr) sum += d;
        return sum / arr.length;
    }

    private static Double getStdDev(Double[] data, Double mean)
    {
        Double temp = 0.0;
        for(Double a :data)
            temp += (mean-a)*(mean-a);
        return Math.sqrt(temp/data.length);
    }

    private static Double getProportion(Double[] data, int function) {
        double goal;
        double numCorrect = 0.0;
        switch (function) {
            case 1: goal = 0.01;
                break;
            case 2: goal = 100;
                break;
            case 3: goal = 0.01;
                break;
            case 4: goal = 0.05;
                break;
            case 5: goal = 100;
                break;
            case 6: goal = 0.00001;
                break;
            case 7: goal = 0.05;
                break;
            case 8: goal = (-450.0 + 0.000001);
                break;
            case 9: goal = (-450.0 + 0.000001);
                break;
            case 10: goal = (-450.0 + 0.000001);
                break;
            case 11: goal = (-450.0 + 0.000001);
                break;
            case 12: goal = (-310.0 + 0.000001);
                break;
            case 13: goal = (390.0 + 0.01);
                break;
            case 14: goal = (-180.0 + 0.01);
                break;
            case 15: goal = (-140.0 + 0.01);
                break;
            case 16: goal = (-330.0 + 0.01);
                break;
            case 17: goal = (-330.0 + 0.01);
                break;
            case 18: goal = (90.0 + 0.01);
                break;
            case 19: goal = (-460.0 + 0.01);
                break;
            case 20: goal = (-130.0 + 0.01);
                break;
            case 21: goal = (-300.0 + 0.01);
                break;
            case 22: goal = (120.0 + 0.01);
                break;
            case 23: goal = (120.0 + 0.01);
                break;
            case 24: goal = (120.0 + 0.1);
                break;
            case 25: goal = (10.0 + 0.1);
                break;
            case 26: goal = (10.0 + 0.1);
                break;
            case 27: goal = (10.0 + 0.1);
                break;
            case 28: goal = (360.0 + 0.1);
                break;
            case 29: goal = (360.0 + 0.1);
                break;
            case 30: goal = (360.0 + 0.1);
                break;
            case 31: goal = (260.0 + 0.1);
                break;
            case 32: goal = (260.0 + 0.1);
                break;
            default: goal = 0.0;
        }
        for(Double d:data) {
            if(d <= goal) {
                numCorrect++;
            }
        }
        return numCorrect/numRuns;
    }
}