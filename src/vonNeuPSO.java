/**
 * Created by Niall on 12/01/2016.
 */
public class vonNeuPSO extends PSOProcess {

    public vonNeuPSO() {}

    //This method is slightly hacked together atm, may return and use
    //a circular array.
    @Override
    public double[] findLocalGBest(int particleNumber) {
        double[] localFitnesses = new double[5];
        if(particleNumber == swarmSize-2) {
            localFitnesses[0] = evaluateFit(bestPositions[swarmSize-4]);
            localFitnesses[1] = evaluateFit(bestPositions[swarmSize-3]);
            localFitnesses[2] = evaluateFit(bestPositions[swarmSize-2]);
            localFitnesses[3] = evaluateFit(bestPositions[swarmSize-1]);
            localFitnesses[4] = evaluateFit(bestPositions[0]);

            int index = getMinPos(localFitnesses);
            if(index == 0) {
                localBestIndex = swarmSize-4;
            }
            else if(index == 1) {
                localBestIndex = swarmSize-3;
            }
            else if(index == 3) {
                localBestIndex = swarmSize-1;
            }
            else if(index == 4) {
                localBestIndex = 0;
            }
            else {
                localBestIndex = swarmSize-2;
            }
        }
        else if(particleNumber == swarmSize-1) {
            localFitnesses[0] = evaluateFit(bestPositions[swarmSize-3]);
            localFitnesses[1] = evaluateFit(bestPositions[swarmSize-2]);
            localFitnesses[2] = evaluateFit(bestPositions[swarmSize-1]);
            localFitnesses[3] = evaluateFit(bestPositions[0]);
            localFitnesses[4] = evaluateFit(bestPositions[1]);

            int index = getMinPos(localFitnesses);
            if(index == 0) {
                localBestIndex = swarmSize-3;
            }
            else if(index == 1) {
                localBestIndex = swarmSize-2;
            }
            else if(index == 3) {
                localBestIndex = 0;
            }
            else if(index == 4) {
                localBestIndex = 1;
            }
            else {
                localBestIndex = swarmSize-1;
            }
        }
        else if(particleNumber == 0) {
            localFitnesses[0] = evaluateFit(bestPositions[swarmSize-2]);
            localFitnesses[1] = evaluateFit(bestPositions[swarmSize-1]);
            localFitnesses[2] = evaluateFit(bestPositions[0]);
            localFitnesses[3] = evaluateFit(bestPositions[1]);
            localFitnesses[4] = evaluateFit(bestPositions[2]);

            int index = getMinPos(localFitnesses);
            if(index == 0) {
                localBestIndex = swarmSize-2;
            }
            else if(index == 1) {
                localBestIndex = swarmSize-1;
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
            localFitnesses[0] = evaluateFit(bestPositions[swarmSize-1]);
            localFitnesses[1] = evaluateFit(bestPositions[0]);
            localFitnesses[2] = evaluateFit(bestPositions[1]);
            localFitnesses[3] = evaluateFit(bestPositions[2]);
            localFitnesses[4] = evaluateFit(bestPositions[3]);

            int index = getMinPos(localFitnesses);
            if(index == 0) {
                localBestIndex = swarmSize-1;
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

            int index = getMinPos(localFitnesses);
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
