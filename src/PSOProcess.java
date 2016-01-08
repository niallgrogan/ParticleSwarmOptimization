import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Vector;

public class PSOProcess implements Constants{

    Vector<Particle> swarm = new Vector<Particle>(swarmSize);
    private double[][] bestPositions = new double[swarmSize][dimensions];
    private double[] bestFitnesses = new double[swarmSize];
    private int globalBestIndex;
    private double[] globalFitnesses = new double[numIterations];

    public PSOProcess() {}

    public void initialise() {
        for(int i=0; i<swarm.capacity(); i++)
        {
            Particle p = new Particle(dimensions);
            swarm.add(p);
            bestPositions[i] = p.getP();
        }
        findGBest();
    }

    private void findGBest() {
        for(int i=0; i<bestPositions.length; i++)
        {
            bestFitnesses[i] = evaluateFit(bestPositions[i]);
        }
        globalBestIndex = getMinPos(bestFitnesses);
//        System.out.println("gbest = "+bestFitnesses[globalBestIndex]);

    }

    private int getMinPos(double[] fitnesses)
    {
        int pos = 0;
        double minVal = fitnesses[0];
        for(int i=0; i<fitnesses.length; i++)
        {
            if(fitnesses[i] < minVal)
            {
                pos = i;
                minVal = fitnesses[i];
            }
        }
        return pos;
    }

    private double evaluateFit(double[] p) {
        //Sphere function
        double fitness = 0;
        for (int i=0; i<dimensions; i++)
        {
            fitness = fitness + Math.pow(p[i],2);
        }
//        if(fitness < 0.01) {System.out.println("Eurekaaaaaaaaaaaaaaaaaaaaa");}
        return fitness;
//        //Generalized Rosenbrock
//        double fitness = 0;
//        for(int i=0; i<dimensions-1; i++)
//        {
//            fitness = fitness + (100*Math.pow(p[i+1] - Math.pow(p[i],2),2)+Math.pow((p[i]-1),2));
//        }
//        return fitness;
    }

    public void execute() {

        for (int j=0; j<numIterations; j++)
        {
            for(int i=0; i<swarm.capacity(); i++)
            {
                double r1 = Math.random();
                double r2 = Math.random();

                Particle p = swarm.elementAt(i);

                //Getting our pBest and gBest
                double[] pbest = bestPositions[i];
                double[] gbest = bestPositions[globalBestIndex];
                double[] newVel = new double[dimensions];
                double[] newPos = new double[dimensions];

                //PSO Equations with Constriction Factor
                for(int k=0; k<dimensions; k++)
                {
                    newVel[k] = constriction*(p.getV()[k] + r1*c1*(pbest[k] - p.getP()[k]) + r2*c2*(gbest[k] - p.getP()[k]));
//                    //Limiting velocity
                    if(newVel[k] > Vmax) {newVel[k] = Vmax;}

                    newPos[k] = p.getP()[k] + newVel[k];
                    //Implementing a reflecting boundary
                    if(newPos[k] > upperBound) {
                        double diff = newPos[k] - upperBound;
                        newPos[k] = newPos[k] - 2*diff;
                    }
                    else if(newPos[k] < lowerBound) {
                        double diff = Math.abs(newPos[k] - lowerBound);
                        newPos[k] = newPos[k] + 2*diff;
                    }
                }
                for(int t=0;t<dimensions; t++){
//                    System.out.print(p.getP()[t] + ", ");
//                    System.out.print(newVel[t] + ", ");

                }
                //Setting new particle velocity and position
                p.setP(newPos);
                p.setV(newVel);

                if(evaluateFit(newPos) < evaluateFit(pbest))
                {
                    bestPositions[i] = newPos;
                }
                findGBest();
            }
            //Update global best
//            findGBest();
            globalFitnesses[j] = evaluateFit(bestPositions[globalBestIndex]);
         //   System.out.println("Best = "+globalFitnesses[j]);
        }
        //System.out.println("***");
        for(int k=0; k<bestPositions.length; k++) {
            //System.out.print(evaluateFit(bestPositions[k]) + "[" + k + "],\n");
        }
        findGBest();
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("outputs.csv");
            fileWriter.append("hey");
            for(int i=0; i<globalFitnesses.length; i++)
            {
                fileWriter.append(Double.toString(globalFitnesses[i]));
                fileWriter.append(",\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(evaluateFit(bestPositions[globalBestIndex]));
    }
}
