/**
 * This class represents the corridor through a fixed point. The class can also evaluate whether a given point lies
 * within the corridor.
 *
 * @author Patrick F. H. Sonnentag
 */
public class Corridor {
    private final double theta;   // Theta of the point A in polar coordinates
    private final double a_x; // x-value of the point A
    private final double a_y; // y-value of the point A
    // Constants which are needed multiple times and are calculated only once
    private double tan;
    private double sin;
    private double cos;

    /**
     * Constructor which defines the corridor through the given point A.
     */
    public Corridor(double theta, double a_x, double a_y) {
        this.theta = theta;
        this.a_x = a_x;
        this.a_y = a_y;
    }

    /**
     * Calculates values which are needed multiple times in the definitions of the different walls which make up the
     * corridor.
     *
     * @param location If it is false we only need the left walls & the according constants, is it true/1 we only need
     *                 the right walls and the constants corresponding to that.
     */
    private void defineConstants(boolean location) {
        double alpha;
        if (!location) {
            alpha = theta / 2;
        } else {
            alpha = (theta + Math.PI) / 2;
        }
        this.tan = Math.tan(alpha);
        this.sin = Math.sin(alpha);
        this.cos = Math.cos(alpha);
    }

    public boolean isInCorridor(double x, double y) {
        // The point to be examined is left oft point A
        if (x <= a_x) {
            defineConstants(false);
            // y-value of the outer wall for x
            double outer_y = tan * (x - a_x + sin) + a_y + cos;
            if (outer_y < y) {
                return false;
            }
            // y-value of the inner wall for x
            double inner_y = tan * (x - a_x) + a_y;
            return !(inner_y > y);
        } else {
            defineConstants(true);
            // y-value of the outer wall for x
            double outer_y = tan * (x - a_x - sin) + a_y - cos;
            if (outer_y < y) {
                return false;
            }
            // y-value of the inner wall for x
            double inner_y = tan * (x - a_x) + a_y;
            return !(inner_y > y);
        }
    }
}