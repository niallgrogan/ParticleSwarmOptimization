/**
 * Created by Niall on 12/01/2016.
 */
public class lBestPSO extends PSOProcess{

    public lBestPSO(String function) {
        fitnessFunction = new Functions(function);
        bestPositions = new double[finalSwarmSize][fitnessFunction.dimensions];
        secondBestPositions = new double[finalSwarmSize][fitnessFunction.dimensions];
        globalBests = new double[finalSwarmSize][fitnessFunction.dimensions];
    }

    //This method is slightly hacked together atm, may return and use
    //a circular array.
    @Override
    public double[] findLocalGBest(int particleNumber, int currentSwarmSize) {
        if(particleNumber == 0) {
            double[] localFitnesses = new double[3];
            localFitnesses[0] = evaluateFit(bestPositions[currentSwarmSize-1]);
            localFitnesses[1] = evaluateFit(bestPositions[0]);
            localFitnesses[2] = evaluateFit(bestPositions[1]);

            int index = getMinPos(localFitnesses, localFitnesses.length);
            if(index == 0) {
                localBestIndex = currentSwarmSize-1;
            }
            else if(index == 2) {
                localBestIndex = 1;
            }
            else {
                localBestIndex = 0;
            }
        }
        else if(particleNumber == currentSwarmSize-1) {
            double[] localFitnesses = new double[3];
            localFitnesses[0] = evaluateFit(bestPositions[currentSwarmSize-2]);
            localFitnesses[1] = evaluateFit(bestPositions[currentSwarmSize-1]);
            localFitnesses[2] = evaluateFit(bestPositions[0]);

            int index = getMinPos(localFitnesses, localFitnesses.length);
            if(index == 0) {
                localBestIndex = currentSwarmSize-2;
            }
            else if(index == 2) {
                localBestIndex = 0;
            }
            else {
                localBestIndex = currentSwarmSize-1;
            }
        }
        else {
            double[] localFitnesses = new double[3];
            localFitnesses[0] = evaluateFit(bestPositions[particleNumber - 1]);
            localFitnesses[1] = evaluateFit(bestPositions[particleNumber]);
            localFitnesses[2] = evaluateFit(bestPositions[particleNumber + 1]);

            int index = getMinPos(localFitnesses, localFitnesses.length);
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
