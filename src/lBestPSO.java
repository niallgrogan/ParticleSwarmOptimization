import java.util.ArrayList;

public class lBestPSO extends PSOProcess{

    public lBestPSO(String function) {
        swarm = new ArrayList<>();
        fitnessFunction = new Functions(function);
    }

    @Override
    public double[] findLocalGBest(int particleNumber, int currentSwarmSize) {
        if(particleNumber == 0) {
            double[] localFitnesses = new double[3];
            localFitnesses[0] = evaluateFit(swarm.get(currentSwarmSize-1).getBestPosition());
            localFitnesses[1] = evaluateFit(swarm.get(0).getBestPosition());
            localFitnesses[2] = evaluateFit(swarm.get(1).getBestPosition());

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
            localFitnesses[0] = evaluateFit(swarm.get(currentSwarmSize-2).getBestPosition());
            localFitnesses[1] = evaluateFit(swarm.get(currentSwarmSize-1).getBestPosition());
            localFitnesses[2] = evaluateFit(swarm.get(0).getBestPosition());

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
            localFitnesses[0] = evaluateFit(swarm.get(particleNumber-1).getBestPosition());
            localFitnesses[1] = evaluateFit(swarm.get(particleNumber).getBestPosition());
            localFitnesses[2] = evaluateFit(swarm.get(particleNumber+1).getBestPosition());

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
        return swarm.get(localBestIndex).getBestPosition();
    }
}
