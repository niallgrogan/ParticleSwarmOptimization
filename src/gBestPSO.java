import java.util.ArrayList;

public class gBestPSO extends PSOProcess {

    public gBestPSO(int function) {
        swarm = new ArrayList<>();
        fitnessFunction = new Functions(function);
        fitFunc = new Functions_K(fitnessFunction.activeFunction, fitnessFunction.dimensions,
                fitnessFunction.upperBound, fitnessFunction.lowerBound);
    }

    @Override
    public Double[] findLocalGBest(int particleNumber, int currentSwarmSize) {
        Double[] bestFitnesses = new Double[currentSwarmSize];
        for(int i=0; i<bestFitnesses.length; i++)
        {
            bestFitnesses[i] = evaluateFit(swarm.get(i).getBestPosition());
        }
        localBestIndex = getMinPos(bestFitnesses, currentSwarmSize);
        return swarm.get(localBestIndex).getBestPosition();
    }
}
