import Curves.Curve;
import Curves.Ellipse;
import Curves.HyperbolicCosine;
import Curves.Polynomial;

import java.util.concurrent.ThreadLocalRandom;

/**
 * It contains the searching logic for the biggest sofa for a given class of curves. It works by searching for the
 * maximum with each coefficient individually. With the given delta_x, delta_y and x_stepSize the area is accurate
 * to five significant figures.
 * Accuracy measured with Hammersleys Sofa.
 *
 * @author Patrick F. H. Sonnentag
 */
public class Search {

    private final Curve curve;
    private final double[] coefficients;  // Input should be as good as possible for quick convergence
    private final R2 points;

    public Search() {
        points = new R2(0.0035, 0.0025);
        coefficients = new double[]{0.5, 0.5};
        curve = new Ellipse(coefficients);
        // Optimize forever because we'll stop it manually either way
        while(true) {
            optimizeOnce(0.0001, 0.1);
        }
    }

    /**
     * !! WARNING !! Experimental approach, requires some manual tweaking
     * Moves each coefficient once into the "right" direction by stepSize. It always takes the direction with the least
     * losses. But it must always move to avoid being trapped in a local maximum or a region which appears as such
     */
    private void optimizeOnce(double origin, double bound) {
        double area = 0;
        for (int i = 0; i < coefficients.length; i++) {
            /*
             * Random number to avoid being trapped in loops
             * Big values in small intervals & small values in bigger intervals for quicker convergence
             */
            double stepSize = ThreadLocalRandom.current().nextDouble(origin, bound);
            // Plus-direction area
            coefficients[i] += stepSize;
            double new_area_plus = getSofaArea();
            // Minus-direction area
            coefficients[i] -= 2 * stepSize;
            double new_area_minus = getSofaArea();

            // Area is bigger in plus-direction
            if (new_area_plus > new_area_minus) {
                coefficients[i] += 2 * stepSize;    // Reset coefficients because that is the "bigger" direction
                area = new_area_plus;
            } else {    // Area is bigger in minus-direction
                // Here we don't need to reset the coefficient
                area = new_area_minus;
            }
        }
        String coeff_String = "";
        for (double coefficient : coefficients) {
            coeff_String += coefficient + ", ";
        }
        // TODO round to signifcan digits
        System.out.println(area + ", " + coeff_String);
    }

    /**
     * Method which can always be called to calculate the current sofa-size
     */
    private double getSofaArea() {
        Sofa sofa = new Sofa(curve, points.getPoints(), 0.0097);
        return sofa.getArea();
    }
}