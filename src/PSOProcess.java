import java.util.Random;
import java.util.Vector;

public class PSOProcess implements Constants{

    Vector<Particle> swarm = new Vector<Particle>(swarmSize);
    private double[][] bestPositions = new double[swarmSize][dimensions];
    private double[] bestFitnesses = new double[swarmSize];
    private int globalBestIndex;

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
        //Schaffer(2D) Function
        double fitness = 0.0;
        double numer = Math.pow(Math.sin(p[0]*p[0] - p[1]*p[1]),2)-0.5;
        double denom = Math.pow((1.0 + 0.001*(p[0]*p[0] + p[1]*p[1])),2);
        fitness = 0.5 + (numer/denom);
        return fitness;
    }

    public void execute() {

        for (int j=0; j<numIterations; j++)
        {
            for(int i=0; i<swarm.capacity(); i++)
            {

                Particle p = swarm.elementAt(i);

                //Getting our pBest and gBest
                double[] pbest = bestPositions[i];
                double[] gbest = bestPositions[globalBestIndex];
                double[] newVel = new double[dimensions];
                double[] newPos = new double[dimensions];

                //PSO Equations with Constriction Factor
                for(int k=0; k<dimensions; k++)
                {
                    newVel[k] = constriction*(p.getV()[k] + new Random().nextDouble()*c1*(pbest[k] - p.getP()[k]) + new Random().nextDouble()*c2*(gbest[k] - p.getP()[k]));
                    //Limiting velocity
                    if(newVel[k] > Vmax) {newVel[k] = Vmax;}
                    else if(newVel[k] < -Vmax) {newVel[k] = -Vmax;}

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
                //Setting new particle velocity and position
                p.setP(newPos);
                p.setV(newVel);

                if(evaluateFit(newPos) < evaluateFit(pbest))
                {
                    bestPositions[i] = newPos;
                }
                findGBest();
            }
            System.out.println(evaluateFit(bestPositions[globalBestIndex]));
        }
        System.out.println(evaluateFit(bestPositions[globalBestIndex]));
    }
}
