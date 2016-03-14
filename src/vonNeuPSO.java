import java.util.ArrayList;

public class vonNeuPSO extends PSOProcess {

    public vonNeuPSO(String function) {
        swarm = new ArrayList<>();
        fitnessFunction = new Functions(function);
    }

    private double[] findGlobalBest(int currentSwarmSize) {
        double[] bestFitnesses = new double[currentSwarmSize];
        for(int i=0; i<bestFitnesses.length; i++)
        {
            bestFitnesses[i] = evaluateFit(swarm.get(i).getBestPosition());
        }
        localBestIndex = getMinPos(bestFitnesses, currentSwarmSize);
        return swarm.get(localBestIndex).getBestPosition();
    }

    @Override
    public double[] findLocalGBest(int particleNumber, int currentSwarmSize) {
        //This is a hack for dealing with problem of small swarm sizes
        if(currentSwarmSize <= 5) {
            return findGlobalBest(currentSwarmSize);
        }
        else {
            double[] localFitnesses = new double[5];
            if(particleNumber == currentSwarmSize-2) {
                localFitnesses[0] = evaluateFit(swarm.get(currentSwarmSize-4).getBestPosition());
                localFitnesses[1] = evaluateFit(swarm.get(currentSwarmSize-3).getBestPosition());
                localFitnesses[2] = evaluateFit(swarm.get(currentSwarmSize-2).getBestPosition());
                localFitnesses[3] = evaluateFit(swarm.get(currentSwarmSize-1).getBestPosition());
                localFitnesses[4] = evaluateFit(swarm.get(0).getBestPosition());

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
                localFitnesses[0] = evaluateFit(swarm.get(currentSwarmSize-3).getBestPosition());
                localFitnesses[1] = evaluateFit(swarm.get(currentSwarmSize-2).getBestPosition());
                localFitnesses[2] = evaluateFit(swarm.get(currentSwarmSize-1).getBestPosition());
                localFitnesses[3] = evaluateFit(swarm.get(0).getBestPosition());
                localFitnesses[4] = evaluateFit(swarm.get(1).getBestPosition());

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
                localFitnesses[0] = evaluateFit(swarm.get(currentSwarmSize-2).getBestPosition());
                localFitnesses[1] = evaluateFit(swarm.get(currentSwarmSize-1).getBestPosition());
                localFitnesses[2] = evaluateFit(swarm.get(0).getBestPosition());
                localFitnesses[3] = evaluateFit(swarm.get(1).getBestPosition());
                localFitnesses[4] = evaluateFit(swarm.get(2).getBestPosition());

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
                localFitnesses[0] = evaluateFit(swarm.get(currentSwarmSize-1).getBestPosition());
                localFitnesses[1] = evaluateFit(swarm.get(0).getBestPosition());
                localFitnesses[2] = evaluateFit(swarm.get(1).getBestPosition());
                localFitnesses[3] = evaluateFit(swarm.get(2).getBestPosition());
                localFitnesses[4] = evaluateFit(swarm.get(3).getBestPosition());
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
                localFitnesses[0] = evaluateFit(swarm.get(particleNumber-2).getBestPosition());
                localFitnesses[1] = evaluateFit(swarm.get(particleNumber-1).getBestPosition());
                localFitnesses[2] = evaluateFit(swarm.get(particleNumber).getBestPosition());
                localFitnesses[3] = evaluateFit(swarm.get(particleNumber+1).getBestPosition());
                localFitnesses[4] = evaluateFit(swarm.get(particleNumber+2).getBestPosition());

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
            return swarm.get(localBestIndex).getBestPosition();
        }
    }
}
