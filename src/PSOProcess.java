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
    private double fitThreshold;
    private double distThreshold;
    private double alpha = defaultAlpha;
    private double beta = defaultBeta;
    private int evaluationIteration = 2;

    public void initialise() {
        for(int i=0; i<initialSwarmSize; i++)
        {
            Particle p = new Particle(fitnessFunction.dimensions, fitnessFunction.upperBound, fitnessFunction.lowerBound);
            swarm[i] = p;
            bestPositions[i] = p.getP();
            secondBestPositions[i] = p.getP();
        }
        currentSwarmSize = initialSwarmSize;
        for(int k=0; k<initialSwarmSize; k++)
        {
            double[] newBest = findLocalGBest(k, currentSwarmSize);
            globalBests[k] = newBest;
        }
        fitThreshold = fitnessFunction.goal/beta;
    }

    private void findGBest() {
        for(int i=0; i<currentSwarmSize; i++)
        {
            bestFitnesses[i] = evaluateFit(bestPositions[i]);
        }
//        System.out.println(getMinPos(bestFitnesses));
        globalBestIndex = getMinPos(bestFitnesses, currentSwarmSize);
    }

    public int getMinPos(double[] fitnesses, int numNeighbours)
    {
        int pos = 0;
        double minVal = fitnesses[0];
        for(int i=0; i<numNeighbours; i++)
        {
            if(fitnesses[i] < minVal)
            {
                pos = i;
                minVal = fitnesses[i];
            }
        }
        return pos;
    }

    public double getLongestDiagonal() {
        double newL;
        double L = 0;
        for(int i=0; i<currentSwarmSize; i++) {
            for(int j=0; j<currentSwarmSize; j++) {
                newL = getDistDiff(swarm[i].getP(),swarm[j].getP());
                if(newL > L) {
                    L = newL;
                }
            }
        }
        return L;
    }

    public double evaluateFit(double[] p) {
        return fitnessFunction.findFitness(p);
    }

    public abstract double[] findLocalGBest(int particleNumber, int currentSwarmSize);

    public double[] rouletteWheel(int i) {
//        double rand = new Random().nextDouble();
//        if(rand <= 1.0/3.0) {
//            return thirdBestPositions[i];
//        }
//        else {
            return bestPositions[i];
//        }
    }

    public double getDistDiff(double[] pos1, double[] pos2) {
        double diff = 0;
        for(int i=0; i<fitnessFunction.dimensions; i++) {
            //TODO - Find out which of these is better
//            diff += Math.abs(pos1[i]) - Math.abs(pos2[i]);
            diff += Math.abs(pos1[i] - pos2[i]);
        }
        return diff;
    }

    public double[] execute() {

        for (int j=0; j<numIterations; j++)
        {
            if(j==evaluationIteration) {
                double dist = getLongestDiagonal();
                distThreshold = dist/alpha;
            }

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

//                if(inBounds) {
                    if(evaluateFit(newPos) < evaluateFit(pbest))
                    {
                        secondBestPositions[i] = bestPositions[i];
                        bestPositions[i] = newPos;

                        //Thresholds only set at j = 200 iterations
                        if(j > evaluationIteration) {
                            //Checking if particle should split
                            if (Math.abs(evaluateFit(bestPositions[i]) - evaluateFit(secondBestPositions[i])) < fitThreshold) {
                                if(getDistDiff(bestPositions[i], secondBestPositions[i]) > distThreshold) {
                                    //Prevent swarm getting too big
                                    if(currentSwarmSize < finalSwarmSize) {
                                        //Increment swarm size
                                        currentSwarmSize++;
                                        Particle newP = new Particle(fitnessFunction.dimensions, fitnessFunction.upperBound, fitnessFunction.lowerBound);
                                        //Give new particle position of old particle
                                        newP.setP(p.getP());
//                                    System.out.println("New PArticles");
                                        //Generate new velocity using half-diff method
                                        swarm[currentSwarmSize-1] = p;
                                        bestPositions[currentSwarmSize-1] = secondBestPositions[i];
                                        secondBestPositions[currentSwarmSize-1] = secondBestPositions[i];
                                    }
                                }
                            }
                        }
                    }

//                }
            }
            for(int k=0; k<currentSwarmSize; k++)
            {
                double[] newBest = findLocalGBest(k, currentSwarmSize);
                if(evaluateFit(newBest) < evaluateFit(globalBests[k])) {
                    globalBests[k] = newBest;
                }
            }
            findGBest();
            globalFitnessArray[j] = evaluateFit(bestPositions[globalBestIndex]);
//            System.out.println(evaluateFit(bestPositions[globalBestIndex]));
        }
        System.out.println(evaluateFit(bestPositions[globalBestIndex]));
        return globalFitnessArray;
    }
}
