import java.util.ArrayList;

public class lBestPSO extends PSOProcess{

    public lBestPSO(int function) {
        swarm = new ArrayList<>();
        fitnessFunction = new Functions(function);
        fitFunc = new Functions_K(fitnessFunction.activeFunction, fitnessFunction.dimensions,
                fitnessFunction.upperBound, fitnessFunction.lowerBound);
    }

    //This method is slightly hacked together atm, may return and use
    //a circular array.
    @Override
    public Double[] findLocalGBest(int particleNumber) {
        if(particleNumber == 0) {
            Double[] localFitnesses = new Double[3];
            localFitnesses[0] = evaluateFit(swarm.get(swarmSize-1).getBestPosition());
            localFitnesses[1] = evaluateFit(swarm.get(0).getBestPosition());
            localFitnesses[2] = evaluateFit(swarm.get(1).getBestPosition());

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
            Double[] localFitnesses = new Double[3];
            localFitnesses[0] = evaluateFit(swarm.get(swarmSize-2).getBestPosition());
            localFitnesses[1] = evaluateFit(swarm.get(swarmSize-1).getBestPosition());
            localFitnesses[2] = evaluateFit(swarm.get(0).getBestPosition());

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
            Double[] localFitnesses = new Double[3];
            localFitnesses[0] = evaluateFit(swarm.get(particleNumber-1).getBestPosition());
            localFitnesses[1] = evaluateFit(swarm.get(particleNumber).getBestPosition());
            localFitnesses[2] = evaluateFit(swarm.get(particleNumber+1).getBestPosition());

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
        return swarm.get(localBestIndex).getBestPosition();
    }
}
