import java.util.ArrayList;
import java.util.Random;

public abstract class PSOProcess implements Constants{

    public PSOProcess() {}

    public Functions fitnessFunction;
    //May want to add an initial capacity here
    ArrayList<Particle> swarm;
    public int localBestIndex;
    private int globalBestIndex;
    private int globalWorstIndex;
    public double[] globalFitnessArray = new double[numIterations];
    private int currentSwarmSize;
    private double fitThreshold;
    private double distThreshold;
    private double alpha = defaultAlpha;
    private double beta = defaultBeta;
    private int evaluationIteration = 2;
    public boolean addedParticle = false;

    public void initialise() {
        for(int i=0; i<initialSwarmSize; i++)
        {
            Particle p = new Particle(fitnessFunction.dimensions, fitnessFunction.upperBound, fitnessFunction.lowerBound);
            swarm.add(p);
        }
        currentSwarmSize = initialSwarmSize;
        for(int k=0; k<initialSwarmSize; k++)
        {
            double[] newBest = findLocalGBest(k, currentSwarmSize);
            swarm.get(k).setNeighbourhoodBest(newBest);
        }
        fitThreshold = fitnessFunction.goal/beta;
    }

    private void findGBest() {
        double[] bestFitnesses = new double[currentSwarmSize];
        for(int i=0; i<currentSwarmSize; i++)
        {
            bestFitnesses[i] = evaluateFit(swarm.get(i).getBestPosition());
        }
        globalBestIndex = getMinPos(bestFitnesses, currentSwarmSize);
        globalWorstIndex = getMaxPos(bestFitnesses, currentSwarmSize);
    }

    public int getMaxPos(double[] fitnesses, int numNeighbours)
    {
        int pos = 0;
        double maxVal = fitnesses[0];
        for(int i=0; i<numNeighbours; i++)
        {
            if(fitnesses[i] > maxVal)
            {
                pos = i;
                maxVal = fitnesses[i];
            }
        }
        return pos;
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
                newL = getDistDiff(swarm.get(i).getP(),swarm.get(j).getP());
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
            return swarm.get(i).getBestPosition();
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

    public void removeWorstParticle() {
        findGBest();
        if(currentSwarmSize > lowestSwarmSize) {
            swarm.remove(globalWorstIndex);
            currentSwarmSize--;
        }
    }

    public boolean execute(int iteration) {

        addedParticle = false;
        if(iteration==evaluationIteration) {
            double dist = getLongestDiagonal();
            distThreshold = dist/alpha;
        }

        for(int i=0; i<currentSwarmSize; i++)
        {
            Particle p = swarm.get(i);

            double[] pbest = p.getBestPosition();
            double[] gbest = p.getNeighbourhoodBest();
            double[] empTerm = rouletteWheel(i);
            double[] newVel = new double[fitnessFunction.dimensions];
            double[] newPos = new double[fitnessFunction.dimensions];
            boolean inBounds = true;

            //PSO Equations with Constriction Factor
            for(int k=0; k<fitnessFunction.dimensions; k++)
            {
                newVel[k] = constriction*(p.getV()[k] + new Random().nextDouble()*c1*(empTerm[k] - p.getP()[k]) + new Random().nextDouble()*c2*(gbest[k] - p.getP()[k]));
                if(newVel[k] > Vmax) {newVel[k] = Vmax;}
                else if(newVel[k] < -Vmax) {newVel[k] = -Vmax;}

                newPos[k] = p.getP()[k] + newVel[k];
                if(newPos[k] > fitnessFunction.upperBound | newPos[k] < fitnessFunction.lowerBound) {
                    inBounds = false;
                }
            }
            p.setP(newPos);
            p.setV(newVel);

            if(inBounds) {
                if(evaluateFit(newPos) < evaluateFit(pbest))
                {
                    p.setSecondBestPosition(p.getBestPosition());
                    p.setBestPosition(newPos);

                    if(iteration > evaluationIteration) {
                        if (Math.abs(evaluateFit(p.getBestPosition()) - evaluateFit(p.getSecondBestPosition())) < fitThreshold) {
                            if(getDistDiff(p.getBestPosition(), p.getSecondBestPosition()) > distThreshold) {
                                if(currentSwarmSize < finalSwarmSize) {
                                    Particle newP = new Particle(fitnessFunction.dimensions, fitnessFunction.upperBound, fitnessFunction.lowerBound);
                                    newP.setP(p.getP());
                                    //TODO - Figure this out
                                    swarm.add(currentSwarmSize,p);
                                    newP.setBestPosition(p.getSecondBestPosition());
                                    newP.setSecondBestPosition(p.getSecondBestPosition());
                                    addedParticle = true;
                                    currentSwarmSize++;
                                }
                            }
                        }
                    }
                }
            }
        }

        for(int k=0; k<currentSwarmSize; k++)
        {
            double[] newBest = findLocalGBest(k, currentSwarmSize);
            if(evaluateFit(newBest) < evaluateFit(swarm.get(k).getNeighbourhoodBest())) {
                swarm.get(k).setNeighbourhoodBest(newBest);
            }
        }
        findGBest();
        globalFitnessArray[iteration] = evaluateFit(swarm.get(globalBestIndex).getBestPosition());
        if(iteration == numIterations-1) {
            System.out.println(evaluateFit(swarm.get(globalBestIndex).getBestPosition()));
        }
        return addedParticle;
    }
}
