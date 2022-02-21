package Curves;

import java.util.ArrayList;

/**
 * This class is a basis for all kind of curves and forces their implementations to be as clean as possible
 *
 * @author Patrick F. H. Sonnentag
 */
public abstract class Curve {

    protected double[] coefficients;

    public Curve(double[] coefficients) {
        this.coefficients = coefficients;
    }

    /**
     * Every curve must be able to assign each point on it the angle from polar coordinates
     */
    public abstract double get_f_of_x(double x);

    /**
     * Every curve must be able to assign each point on it the angle from polar coordinates
     */
    public double getTheta(double x, double y) {
        double r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        // Math.acos determines the value in the interval [0, pi], which is the range of theta
        return Math.acos(x / r);    // Return theta
    }

    /**
     * Returns an ArrayList of all points along which the sofa is supposed to move. All these points will be the point
     * A in the future. The parameter gives the x-distance between to evaluated points.
     */
    public ArrayList<double[]> getRelevantIntervall(double x_stepSize) {
        ArrayList<double[]> relevantPoints = new ArrayList<>();
        // As long as this is true, it can evaluate new points
        double x = 0;
        // Loops forever until break-statement is called
        while (true) {
            double y = get_f_of_x(x);
            /*
             * Check condition for termination; terminates when a root is found or when there is no root soon enough.
             * That means that the curve still continues when it oversteps the maximal length of the sofa. In that
             * second case the method returns an empty ArrayList.
             * As there are solely positive functions we need to check whether y is not positive or of x is not in range
             */
            if (!(y >= 0)) {
                break;
            }
            if (!(x <= 1 + Math.sqrt(2))) {
                return new ArrayList<>();
            }
            double theta = getTheta(x, y);
            relevantPoints.add(new double[]{theta, x, y});
            // Also add corresponding point with negative x-value and fitting angle
            relevantPoints.add(new double[]{Math.PI - theta, -x, y});
            x += x_stepSize;
        }
        return relevantPoints;
    }
}