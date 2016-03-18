import java.util.ArrayList;

public class gBestPSO extends PSOProcess {

    public gBestPSO(int function) {
        swarm = new ArrayList<>();
        fitnessFunction = new Functions(function);
        fitFunc = new Functions_K(fitnessFunction.activeFunction, fitnessFunction.dimensions,
                fitnessFunction.upperBound, fitnessFunction.lowerBound);
    }

    @Override
    public Double[] findLocalGBest(int particleNumber) {
        Double[] bestFitnesses = new Double[swarmSize];
        for(int i=0; i<bestFitnesses.length; i++)
        {
            bestFitnesses[i] = evaluateFit(swarm.get(i).getBestPosition());
        }
        localBestIndex = getMinPos(bestFitnesses);
        return swarm.get(localBestIndex).getBestPosition();
    }
}
