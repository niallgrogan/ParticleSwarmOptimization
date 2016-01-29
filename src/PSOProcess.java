import java.util.Random;

public abstract class PSOProcess implements Constants{

    public PSOProcess() {}

    public Functions fitnessFunction;
    Particle[] swarm = new Particle[finalSwarmSize];
    public double[][] bestPositions;
    public double[][] secondBestPositions;
    public double[][] globalBests;
    public double[] bestFitnesses = new double[finalSwarmSize];
    public int localBestIndex;
    private int globalBestIndex;
    private double[] globalFitnessArray = new double[numIterations];
    private int currentSwarmSize;

    public void initialise() {
        for(int i=0; i<initialSwarmSize; i++)
        {
            Particle p = new Particle(fitnessFunction.dimensions, fitnessFunction.upperBound, fitnessFunction.lowerBound);
            swarm[i] = p;
            bestPositions[i] = p.getP();
            secondBestPositions[i] = p.getP();
        }
        for(int k=0; k<initialSwarmSize; k++)
        {
            double[] newBest = findLocalGBest(k);
            globalBests[k] = newBest;
        }
        currentSwarmSize = initialSwarmSize;
    }

    private void findGBest() {
        for(int i=0; i<currentSwarmSize; i++)
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

    public double[] rouletteWheel(int i) {
        double rand = new Random().nextDouble();
        if(rand <= (double) (1/3)) {
            return secondBestPositions[i];
        }
        else {
            return bestPositions[i];
        }
    }

    public double[] execute() {

        for (int j=0; j<numIterations; j++)
        {
            for(int i=0; i<currentSwarmSize; i++)
            {

                Particle p = swarm[i];

                //Getting our pBest and gBest
                double[] pbest = bestPositions[i];
                double[] gbest = globalBests[i];
                double[] empTerm = rouletteWheel(i);
                double[] newVel = new double[fitnessFunction.dimensions];
                double[] newPos = new double[fitnessFunction.dimensions];
                boolean inBounds = true;

                //PSO Equations with Constriction Factor
                for(int k=0; k<fitnessFunction.dimensions; k++)
                {
                    newVel[k] = constriction*(p.getV()[k] + new Random().nextDouble()*c1*(empTerm[k] - p.getP()[k]) + new Random().nextDouble()*c2*(gbest[k] - p.getP()[k]));
                    //Limiting velocity
                    if(newVel[k] > Vmax) {newVel[k] = Vmax;}
                    else if(newVel[k] < -Vmax) {newVel[k] = -Vmax;}

                    newPos[k] = p.getP()[k] + newVel[k];
                    //Setting boundary conditions
                    if(newPos[k] > fitnessFunction.upperBound | newPos[k] < fitnessFunction.lowerBound) {
                        inBounds = false;
                    }
                }
                //Setting new particle velocity and position
                p.setP(newPos);
                p.setV(newVel);

                if(inBounds) {
                    if(evaluateFit(newPos) < evaluateFit(pbest))
                    {
                        secondBestPositions[i] = bestPositions[i];
                        bestPositions[i] = newPos;
                    }

                    //Checking if particle should split
                    if(getFitDiff(bestPositions[i],secondBestPositions[i]) < fitThreshold) {
                        if(getDistDiff(bestPositions[i], secondBestPositions[i]) > distThreshold) {
                            //Prevent swarm getting too big
                            if(currentSwarmSize < finalSwarmSize) {
                                //Increment swarm size
                                currentSwarmSize++;
                                Particle newP = new Particle(fitnessFunction.dimensions, fitnessFunction.upperBound, fitnessFunction.lowerBound);
                                //Give new particle position of old particle
                                newP.setP(p.getP());
                                //Generate new velocity using half-diff method
                                swarm[currentSwarmSize-1] = p;
                                bestPositions[currentSwarmSize-1] = secondBestPositions[i];
                                secondBestPositions[currentSwarmSize-1] = secondBestPositions[i];
                            }
                        }
                    }
                }
            }
            for(int k=0; k<currentSwarmSize; k++)
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
