/**
 * Created by Niall on 17/11/2015.
 */
public class Velocity {
    private double X;
    private double Y;

    public Velocity(double x, double y) {
        X = x;
        Y = y;
    }

    public void setX(double x)
    {
        X = x;
    }

    public void setY(double y)
    {
        Y = y;
    }

    public double getX()
    {
        return X;
    }

    public double getY()
    {
        return Y;
    }
}
