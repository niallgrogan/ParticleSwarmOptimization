import java.util.ArrayList;

public class gBestPSO extends PSOProcess {

    public gBestPSO(String function) {
        swarm = new ArrayList<>();
        fitnessFunction = new Functions(function);
    }

    @Override
    public double[] findLocalGBest(int particleNumber, int currentSwarmSize) {
        double[] bestFitnesses = new double[currentSwarmSize];
        for(int i=0; i<bestFitnesses.length; i++)
        {
            bestFitnesses[i] = evaluateFit(swarm.get(i).getBestPosition());
        }
        localBestIndex = getMinPos(bestFitnesses, currentSwarmSize);
        return swarm.get(localBestIndex).getBestPosition();
    }
}
