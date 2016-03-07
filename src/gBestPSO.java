/**
 * Created by Niall on 12/01/2016.
 */
public class gBestPSO extends PSOProcess {

    public gBestPSO(String function) {
        fitnessFunction = new Functions(function);
        bestPositions = new double[finalSwarmSize][fitnessFunction.dimensions];
        secondBestPositions = new double[finalSwarmSize][fitnessFunction.dimensions];
        thirdBestPositions = new double[finalSwarmSize][fitnessFunction.dimensions];
        globalBests = new double[finalSwarmSize][fitnessFunction.dimensions];
    }

    @Override
    public double[] findLocalGBest(int particleNumber, int currentSwarmSize) {
        for(int i=0; i<bestPositions.length; i++)
        {
            bestFitnesses[i] = evaluateFit(bestPositions[i]);
        }
        localBestIndex = getMinPos(bestFitnesses, currentSwarmSize);
        return bestPositions[localBestIndex];
    }
}
