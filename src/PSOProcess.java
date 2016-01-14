import java.util.Random;

public abstract class PSOProcess implements Constants{

    Particle[] swarm = new Particle[swarmSize];
    public double[][] bestPositions = new double[swarmSize][dimensions];
    public double[][] globalBests = new double[swarmSize][dimensions];
    public double[] bestFitnesses = new double[swarmSize];
    public int localBestIndex;
    private int globalBestIndex;
    private double[] globalFitnessArray = new double[numIterations];
    public Functions fitnessFunction;

    public PSOProcess() {}

    public void initialise() {
        for(int i=0; i<swarm.length; i++)
        {
            Particle p = new Particle(dimensions);
            swarm[i] = p;
            bestPositions[i] = p.getP();
        }
        for(int k=0; k<swarmSize; k++)
        {
            double[] newBest = findLocalGBest(k);
            globalBests[k] = newBest;
        }
    }

    private void findGBest() {
        for(int i=0; i<bestPositions.length; i++)
        {
            bestFitnesses[i] = evaluateFit(bestPositions[i]);
        }
        globalBestIndex = getMinPos(bestFitnesses);
    }

    public int getMinPos(double[] fitnesses)
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

    public double evaluateFit(double[] p) {
        return fitnessFunction.findFitness(p);
    }

    public abstract double[] findLocalGBest(int particleNumber);

    public double[] execute() {

        for (int j=0; j<numIterations; j++)
        {
            for(int i=0; i<swarm.length; i++)
            {

                Particle p = swarm[i];

                //Getting our pBest and gBest
                double[] pbest = bestPositions[i];
                double[] gbest = globalBests[i];
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
//                    //THIS IS INCORRECT
//                    //Implementing a reflecting boundary
//                    if(newPos[k] > upperBound) {
//                        double diff = newPos[k] - upperBound;
//                        newPos[k] = newPos[k] - 2*diff;
//                    }
//                    else if(newPos[k] < lowerBound) {
//                        double diff = Math.abs(newPos[k] - lowerBound);
//                        newPos[k] = newPos[k] + 2*diff;
//                    }
                }
                //Setting new particle velocity and position
                p.setP(newPos);
                p.setV(newVel);

                if(evaluateFit(newPos) < evaluateFit(pbest))
                {
                    bestPositions[i] = newPos;
                }
            }
            for(int k=0; k<swarmSize; k++)
            {
                double[] newBest = findLocalGBest(k);
                if(evaluateFit(newBest) < evaluateFit(globalBests[k])) {
                    globalBests[k] = newBest;
                }
            }
            findGBest();
            globalFitnessArray[j] = evaluateFit(bestPositions[globalBestIndex]);
        }
        System.out.println(evaluateFit(bestPositions[globalBestIndex]));
        return globalFitnessArray;
    }
}
