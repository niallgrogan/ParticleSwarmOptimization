public class lBestPSO extends PSOProcess{

    public lBestPSO(int function) {
        fitnessFunction = new Functions(function);
        fitFunc = new Functions_K(fitnessFunction.activeFunction, fitnessFunction.dimensions,
                fitnessFunction.upperBound, fitnessFunction.lowerBound);
        bestPositions = new double[swarmSize][fitnessFunction.dimensions];
        globalBests = new double[swarmSize][fitnessFunction.dimensions];
    }

    //This method is slightly hacked together atm, may return and use
    //a circular array.
    @Override
    public double[] findLocalGBest(int particleNumber) {
        if(particleNumber == 0) {
            double[] localFitnesses = new double[3];
            localFitnesses[0] = evaluateFit(bestPositions[swarmSize-1]);
            localFitnesses[1] = evaluateFit(bestPositions[0]);
            localFitnesses[2] = evaluateFit(bestPositions[1]);

            int index = getMinPos(localFitnesses);
            if(index == 0) {
                localBestIndex = swarmSize-1;
            }
            else if(index == 2) {
                localBestIndex = 1;
            }
            else {
                localBestIndex = 0;
            }
        }
        else if(particleNumber == swarmSize-1) {
            double[] localFitnesses = new double[3];
            localFitnesses[0] = evaluateFit(bestPositions[swarmSize-2]);
            localFitnesses[1] = evaluateFit(bestPositions[swarmSize-1]);
            localFitnesses[2] = evaluateFit(bestPositions[0]);

            int index = getMinPos(localFitnesses);
            if(index == 0) {
                localBestIndex = swarmSize-2;
            }
            else if(index == 2) {
                localBestIndex = 0;
            }
            else {
                localBestIndex = swarmSize-1;
            }
        }
        else {
            double[] localFitnesses = new double[3];
            localFitnesses[0] = evaluateFit(bestPositions[particleNumber - 1]);
            localFitnesses[1] = evaluateFit(bestPositions[particleNumber]);
            localFitnesses[2] = evaluateFit(bestPositions[particleNumber + 1]);

            int index = getMinPos(localFitnesses);
            if(index == 0) {
                localBestIndex = particleNumber - 1;
            }
            else if(index == 2) {
                localBestIndex = particleNumber + 1;
            }
            else {
                localBestIndex = particleNumber;
            }
        }
        return bestPositions[localBestIndex];
    }
}
