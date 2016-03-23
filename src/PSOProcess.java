import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public abstract class PSOProcess implements Constants{

    public PSOProcess() {}

    public Functions fitnessFunction;
    public Functions_K fitFunc;
    ArrayList<Particle> swarm;
    public int localBestIndex;
    private int globalBestIndex;
    private Double[] globalFitnessArray = new Double[numIterations];
    private int currentSwarmSize;
    private Double fitThreshold;
    private Double distThreshold;

    public void initialise() {
        for(int i=0; i<initialSwarmSize; i++)
        {
            Particle p = new Particle(fitnessFunction.dimensions,
                    fitnessFunction.upperBound.get(0), fitnessFunction.lowerBound.get(0));
            swarm.add(p);

        }
        currentSwarmSize = initialSwarmSize;
        for(int k=0; k<initialSwarmSize; k++)
        {
            Double[] newBest = findLocalGBest(k, currentSwarmSize);
            swarm.get(k).setNeighbourhoodBest(newBest);
        }
        fitThreshold = fitnessFunction.goal/beta;
        distThreshold = fitnessFunction.upperBound.get(0)/alpha;
    }

    private void findGBest() {
        Double[] bestFitnesses = new Double[currentSwarmSize];
        for(int i=0; i<currentSwarmSize; i++)
        {
            bestFitnesses[i] = evaluateFit(swarm.get(i).getBestPosition());
        }
        globalBestIndex = getMinPos(bestFitnesses, currentSwarmSize);
    }

    public int getMinPos(Double[] fitnesses, int numNeighbours)
    {
        int pos = 0;
        Double minVal = fitnesses[0];
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

    public Double evaluateFit(Double[] p) {
        ArrayList<Double> arr = new ArrayList<Double>(Arrays.asList(p));
        return fitFunc.FitnessCheck(arr);
    }

    public abstract Double[] findLocalGBest(int particleNumber, int currentSwarmSize);

    public Double[] rouletteWheel(int i) {
        double rand = new Random().nextDouble();
        if(rand <= 1.0/3.0) {
            return swarm.get(i).getSecondBestPosition();
        }
        else {
            return swarm.get(i).getBestPosition();
        }
    }

    public Double getDistDiff(Double[] pos1, Double[] pos2) {
        Double diff = 0.0;
        for(int i=0; i<fitnessFunction.dimensions; i++) {
            diff += Math.abs(pos1[i] - pos2[i]);
        }
        return diff;
    }

    public Double[] execute() {

        for (int j=0; j<numIterations; j++)
        {
            for(int i=0; i<currentSwarmSize; i++)
            {
                Particle p = swarm.get(i);

                //Getting our pBest and gBest
                Double[] pbest = p.getBestPosition();
                Double[] gbest = p.getNeighbourhoodBest();
                Double[] empTerm = rouletteWheel(i);
                Double[] newVel = new Double[fitnessFunction.dimensions];
                Double[] newPos = new Double[fitnessFunction.dimensions];
                boolean inBounds = true;

                for(int k=0; k<fitnessFunction.dimensions; k++)
                {
                    newVel[k] = constriction*(p.getV()[k] + new Random().nextDouble()*c1*(empTerm[k] - p.getP()[k]) + new Random().nextDouble()*c2*(gbest[k] - p.getP()[k]));
                    if(newVel[k] > Vmax) {newVel[k] = Vmax;}
                    else if(newVel[k] < -Vmax) {newVel[k] = -Vmax;}

                    newPos[k] = p.getP()[k] + newVel[k];
                    if(newPos[k] > fitnessFunction.upperBound.get(0) |
                            newPos[k] < fitnessFunction.lowerBound.get(0)) {
                        inBounds = false;
                    }
                }
                //Setting new particle velocity and position
                p.setP(newPos);
                p.setV(newVel);

                if(inBounds) {
                    if(evaluateFit(newPos) < evaluateFit(pbest))
                    {
                        p.setSecondBestPosition(p.getBestPosition());
                        p.setBestPosition(newPos);

                        //Thresholds only set at j = 200 iterations
                            //Checking if particle should split
                            if(Math.abs(evaluateFit(p.getBestPosition()) - evaluateFit(p.getSecondBestPosition())) < fitThreshold) {
                                if(getDistDiff(p.getBestPosition(), p.getSecondBestPosition()) > distThreshold) {
                                    if(currentSwarmSize < finalSwarmSize) {
                                        Particle newP = new Particle(fitnessFunction.dimensions,
                                                fitnessFunction.upperBound.get(0), fitnessFunction.lowerBound.get(0));
                                        newP.setP(p.getP());
                                        swarm.add(currentSwarmSize,newP);
                                        Double[] bestNeighbour = findLocalGBest(currentSwarmSize, currentSwarmSize+1);
                                        newP.setNeighbourhoodBest(bestNeighbour);
                                        newP.setBestPosition(p.getSecondBestPosition());
                                        newP.setSecondBestPosition(p.getSecondBestPosition());
                                        currentSwarmSize++;
                                    }
                                }
                            }
                    }

                }
            }
            for(int k=0; k<currentSwarmSize; k++)
            {
                Double[] newBest = findLocalGBest(k, currentSwarmSize);
                if(evaluateFit(newBest) < evaluateFit(swarm.get(k).getNeighbourhoodBest())) {
                    swarm.get(k).setNeighbourhoodBest(newBest);
                }
            }
            findGBest();
            globalFitnessArray[j] = evaluateFit(swarm.get(globalBestIndex).getBestPosition());
//            System.out.println(evaluateFit(bestPositions[globalBestIndex]));
        }
        System.out.println(evaluateFit(swarm.get(globalBestIndex).getBestPosition()));
        return globalFitnessArray;
    }
}
