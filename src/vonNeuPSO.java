import java.util.ArrayList;

public class vonNeuPSO extends PSOProcess {

    public vonNeuPSO(int function) {
        swarm = new ArrayList<>();
        fitnessFunction = new Functions(function);
        fitFunc = new Functions_K(fitnessFunction.activeFunction, fitnessFunction.dimensions,
                fitnessFunction.upperBound, fitnessFunction.lowerBound);
    }

    //This method is slightly hacked together atm, may return and use
    //a circular array.
    @Override
    public Double[] findLocalGBest(int particleNumber) {
        Double[] localFitnesses = new Double[5];
        if(particleNumber == swarmSize-2) {
            localFitnesses[0] = evaluateFit(swarm.get(swarmSize-4).getBestPosition());
            localFitnesses[1] = evaluateFit(swarm.get(swarmSize-3).getBestPosition());
            localFitnesses[2] = evaluateFit(swarm.get(swarmSize-2).getBestPosition());
            localFitnesses[3] = evaluateFit(swarm.get(swarmSize-1).getBestPosition());
            localFitnesses[4] = evaluateFit(swarm.get(0).getBestPosition());

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
            localFitnesses[0] = evaluateFit(swarm.get(swarmSize-3).getBestPosition());
            localFitnesses[1] = evaluateFit(swarm.get(swarmSize-2).getBestPosition());
            localFitnesses[2] = evaluateFit(swarm.get(swarmSize-1).getBestPosition());
            localFitnesses[3] = evaluateFit(swarm.get(0).getBestPosition());
            localFitnesses[4] = evaluateFit(swarm.get(1).getBestPosition());

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
            localFitnesses[0] = evaluateFit(swarm.get(swarmSize-2).getBestPosition());
            localFitnesses[1] = evaluateFit(swarm.get(swarmSize-1).getBestPosition());
            localFitnesses[2] = evaluateFit(swarm.get(0).getBestPosition());
            localFitnesses[3] = evaluateFit(swarm.get(1).getBestPosition());
            localFitnesses[4] = evaluateFit(swarm.get(2).getBestPosition());

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
            localFitnesses[0] = evaluateFit(swarm.get(swarmSize-1).getBestPosition());
            localFitnesses[1] = evaluateFit(swarm.get(0).getBestPosition());
            localFitnesses[2] = evaluateFit(swarm.get(1).getBestPosition());
            localFitnesses[3] = evaluateFit(swarm.get(2).getBestPosition());
            localFitnesses[4] = evaluateFit(swarm.get(3).getBestPosition());

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
            localFitnesses[0] = evaluateFit(swarm.get(particleNumber-2).getBestPosition());
            localFitnesses[1] = evaluateFit(swarm.get(particleNumber-1).getBestPosition());
            localFitnesses[2] = evaluateFit(swarm.get(particleNumber).getBestPosition());
            localFitnesses[3] = evaluateFit(swarm.get(particleNumber+1).getBestPosition());
            localFitnesses[4] = evaluateFit(swarm.get(particleNumber+2).getBestPosition());

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
        return swarm.get(localBestIndex).getBestPosition();

    }
}
