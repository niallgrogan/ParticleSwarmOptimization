public class gBestPSO extends PSOProcess {

    public gBestPSO(int function) {
        fitnessFunction = new Functions(function);
        fitFunc = new Functions_K(fitnessFunction.activeFunction, fitnessFunction.dimensions,
                fitnessFunction.upperBound, fitnessFunction.lowerBound);
        bestPositions = new double[swarmSize][fitnessFunction.dimensions];
        globalBests = new double[swarmSize][fitnessFunction.dimensions];
    }

    @Override
    public double[] findLocalGBest(int particleNumber) {
        for(int i=0; i<bestPositions.length; i++)
        {
            bestFitnesses[i] = evaluateFit(bestPositions[i]);
        }
        localBestIndex = getMinPos(bestFitnesses);
        return bestPositions[localBestIndex];
    }
}
