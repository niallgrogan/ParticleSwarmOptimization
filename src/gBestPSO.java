/**
 * Created by Niall on 12/01/2016.
 */
public class gBestPSO extends PSOProcess {

    public gBestPSO() {}

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
