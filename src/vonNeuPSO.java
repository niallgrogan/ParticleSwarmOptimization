/**
 * Created by Niall on 12/01/2016.
 */
public class vonNeuPSO extends PSOProcess {

    public vonNeuPSO(String function) {
        fitnessFunction = new Functions(function);
        bestPositions = new double[finalSwarmSize][fitnessFunction.dimensions];
        secondBestPositions = new double[finalSwarmSize][fitnessFunction.dimensions];
        globalBests = new double[finalSwarmSize][fitnessFunction.dimensions];
    }

    //This method is slightly hacked together atm, may return and use
    //a circular array.
    @Override
    public double[] findLocalGBest(int particleNumber, int currentSwarmSize) {
        double[] localFitnesses = new double[5];
        if(particleNumber == currentSwarmSize-2) {
            localFitnesses[0] = evaluateFit(bestPositions[currentSwarmSize-4]);
            localFitnesses[1] = evaluateFit(bestPositions[currentSwarmSize-3]);
            localFitnesses[2] = evaluateFit(bestPositions[currentSwarmSize-2]);
            localFitnesses[3] = evaluateFit(bestPositions[currentSwarmSize-1]);
            localFitnesses[4] = evaluateFit(bestPositions[0]);

            int index = getMinPos(localFitnesses, localFitnesses.length);
            if(index == 0) {
                localBestIndex = currentSwarmSize-4;
            }
            else if(index == 1) {
                localBestIndex = currentSwarmSize-3;
            }
            else if(index == 3) {
                localBestIndex = currentSwarmSize-1;
            }
            else if(index == 4) {
                localBestIndex = 0;
            }
            else {
                localBestIndex = currentSwarmSize-2;
            }
        }
        else if(particleNumber == currentSwarmSize-1) {
            localFitnesses[0] = evaluateFit(bestPositions[currentSwarmSize-3]);
            localFitnesses[1] = evaluateFit(bestPositions[currentSwarmSize-2]);
            localFitnesses[2] = evaluateFit(bestPositions[currentSwarmSize-1]);
            localFitnesses[3] = evaluateFit(bestPositions[0]);
            localFitnesses[4] = evaluateFit(bestPositions[1]);

            int index = getMinPos(localFitnesses, localFitnesses.length);
            if(index == 0) {
                localBestIndex = currentSwarmSize-3;
            }
            else if(index == 1) {
                localBestIndex = currentSwarmSize-2;
            }
            else if(index == 3) {
                localBestIndex = 0;
            }
            else if(index == 4) {
                localBestIndex = 1;
            }
            else {
                localBestIndex = currentSwarmSize-1;
            }
        }
        else if(particleNumber == 0) {
            localFitnesses[0] = evaluateFit(bestPositions[currentSwarmSize-2]);
            localFitnesses[1] = evaluateFit(bestPositions[currentSwarmSize-1]);
            localFitnesses[2] = evaluateFit(bestPositions[0]);
            localFitnesses[3] = evaluateFit(bestPositions[1]);
            localFitnesses[4] = evaluateFit(bestPositions[2]);

            int index = getMinPos(localFitnesses, localFitnesses.length);
            if(index == 0) {
                localBestIndex = currentSwarmSize-2;
            }
            else if(index == 1) {
                localBestIndex = currentSwarmSize-1;
            }
            else if(index == 3) {
                localBestIndex = 1;
            }
            else if(index == 4) {
                localBestIndex = 2;
            }
            else {
                localBestIndex = 0;
            }
        }
        else if(particleNumber == 1) {
            localFitnesses[0] = evaluateFit(bestPositions[currentSwarmSize-1]);
            localFitnesses[1] = evaluateFit(bestPositions[0]);
            localFitnesses[2] = evaluateFit(bestPositions[1]);
            localFitnesses[3] = evaluateFit(bestPositions[2]);
            localFitnesses[4] = evaluateFit(bestPositions[3]);

            int index = getMinPos(localFitnesses, localFitnesses.length);
            if(index == 0) {
                localBestIndex = currentSwarmSize-1;
            }
            else if(index == 1) {
                localBestIndex = 0;
            }
            else if(index == 3) {
                localBestIndex = 2;
            }
            else if(index == 4) {
                localBestIndex = 3;
            }
            else {
                localBestIndex = 1;
            }
        }
        else {
            localFitnesses[0] = evaluateFit(bestPositions[particleNumber - 2]);
            localFitnesses[1] = evaluateFit(bestPositions[particleNumber - 1]);
            localFitnesses[2] = evaluateFit(bestPositions[particleNumber]);
            localFitnesses[3] = evaluateFit(bestPositions[particleNumber + 1]);
            localFitnesses[4] = evaluateFit(bestPositions[particleNumber + 2]);

            int index = getMinPos(localFitnesses, localFitnesses.length);
            if(index == 0) {
                localBestIndex = particleNumber - 2;
            }
            else if(index == 1) {
                localBestIndex = particleNumber - 1;
            }
            else if(index == 3) {
                localBestIndex = particleNumber + 1;
            }
            else if(index == 4) {
                localBestIndex = particleNumber + 2;
            }
            else {
                localBestIndex = particleNumber;
            }
        }
        return bestPositions[localBestIndex];

    }
}
