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

    public void initialise() {
        for(int i=0; i<swarmSize; i++)
        {
            Particle p = new Particle(fitnessFunction.dimensions, fitnessFunction.upperBound.get(0), fitnessFunction.lowerBound.get(0));
            swarm.add(p);
        }
        for(int k=0; k<swarmSize; k++)
        {
            Double[] newBest = findLocalGBest(k);
            swarm.get(k).setNeighbourhoodBest(newBest);
        }
    }

    private void findGBest() {
        Double[] bestFitnesses = new Double[swarm.size()];
        for(int i=0; i<bestFitnesses.length; i++)
        {
            bestFitnesses[i] = evaluateFit(swarm.get(i).getBestPosition());
        }
        globalBestIndex = getMinPos(bestFitnesses);
    }

    public int getMinPos(Double[] fitnesses)
    {
        int pos = 0;
        Double minVal = fitnesses[0];
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

    public Double evaluateFit(Double[] p) {
        //TODO - fix the hack below
        ArrayList<Double> arr = new ArrayList<Double>(Arrays.asList(p));
        return fitFunc.FitnessCheck(arr);
    }

    public abstract Double[] findLocalGBest(int particleNumber);

    public Double[] execute() {

        for (int j=0; j<numIterations; j++)
        {
            for(int i=0; i<swarmSize; i++)
            {

                Particle p = swarm.get(i);

                //Getting our pBest and gBest
                Double[] pbest = p.getBestPosition();
                Double[] gbest = p.getNeighbourhoodBest();
                Double[] newVel = new Double[fitnessFunction.dimensions];
                Double[] newPos = new Double[fitnessFunction.dimensions];
                boolean inBounds = true;

                //PSO Equations with Constriction Factor
                for(int k=0; k<fitnessFunction.dimensions; k++)
                {
                    newVel[k] = constriction*(p.getV()[k] + new Random().nextDouble()*c1*(pbest[k] - p.getP()[k]) + new Random().nextDouble()*c2*(gbest[k] - p.getP()[k]));
                    //Limiting velocity
                    if(newVel[k] > Vmax) {newVel[k] = Vmax;}
                    else if(newVel[k] < -Vmax) {newVel[k] = -Vmax;}

                    newPos[k] = p.getP()[k] + newVel[k];
                    //Setting boundary conditions
                    if(newPos[k] > fitnessFunction.upperBound.get(0) | newPos[k] < fitnessFunction.lowerBound.get(0)) {
                        if(fitnessFunction.activeFunction != 14) {
                            inBounds = false;
                        }
                    }
                }
                //Setting new particle velocity and position
                p.setP(newPos);
                p.setV(newVel);

                if(inBounds) {
                    if(evaluateFit(newPos) < evaluateFit(pbest))
                    {
                        p.setBestPosition(newPos);
                    }
                }
            }
            for(int k=0; k<swarmSize; k++)
            {
                Double[] newBest = findLocalGBest(k);
                if(evaluateFit(newBest) < evaluateFit(swarm.get(k).getNeighbourhoodBest())) {
                    swarm.get(k).setNeighbourhoodBest(newBest);
                }
            }
            findGBest();
            globalFitnessArray[j] = evaluateFit(swarm.get(globalBestIndex).getBestPosition());
//            System.out.println(evaluateFit(swarm.get(globalBestIndex).getBestPosition()));
        }
        System.out.println(evaluateFit(swarm.get(globalBestIndex).getBestPosition()));
        return globalFitnessArray;
    }
}
