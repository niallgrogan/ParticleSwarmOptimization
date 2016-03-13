public class Particle implements Constants{

    private double[] pos;
    private double[] vel;
    private double[] bestPosition;
    private double[] secondBestPosition;
    private double[] neighbourhoodBest;

    public Particle(int dimension, double upperBound, double lowerBound)
    {
        pos = new double[dimension];
        vel = new double[dimension];
        for(int i=0; i<dimension; i++) {
            //Set randomly inside problem bounds
            pos[i] = (upperBound - lowerBound) * Math.random() + lowerBound;
            //Set using half diff method
            vel[i] = (((upperBound - lowerBound) * Math.random() + lowerBound) - pos[i]) / 2;
        }
        bestPosition = pos;
        secondBestPosition = pos;
    }

    public void setP(double[] p) {pos = p;}

    public double[] getP() {return pos;}

    public void setV(double[] v) {vel = v;}

    public double[] getV() {return vel;}

    public void setBestPosition(double[] p) {bestPosition = p;}

    public double[] getBestPosition() {return bestPosition;}

    public void setSecondBestPosition(double[] p) {secondBestPosition = p;}

    public double[] getSecondBestPosition() {return secondBestPosition;}

    public void setNeighbourhoodBest(double[] p) {neighbourhoodBest = p;}

    public double[] getNeighbourhoodBest() {return neighbourhoodBest;}


}
