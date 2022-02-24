import Curves.*;

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

    /*
     * Here we document the biggest found values for different curves by just showing the output
     * -Ellipse:
     */
    public Search() {
        points = new R2(0.0035, 0.0025);
        // Best found ellipse with A=2.2078
        coefficients = new double[]{0.6301410922492787, 0.6449725029257345};
        curve = new Ellipse(coefficients);

        // Best found cosh with A=2.1869
        // coefficients = new double[]{-0.7064611151691271, 2.2219398700648094, -1.9119412647101728, 1.2885602155033589};
        // curve = new HyperbolicCosine(coefficients);

        // Best found secant with A=2,20485
        // coefficients = new double[]{-0.41924717520255345, 1.7840190991041047, 1.0604889859660755};
        // curve = new Secant(coefficients);

        // Best found polynomial with A=2.2061
        // coefficients = new double[]{0.6449431433179632, -0.8044557352332703, -0.17559279232830555, -0.9479741917936974, -2.9591514254784634, -4.4303786242418095, -6.465508816923603, -8.54730959620789};
        // curve = new Polynomial(coefficients, 8);

        // Optimize forever because we'll stop it manually either way
        while(true) {
            optimizeOnce(0.0005, 0.001);
        }
    }

    /**
     * !! WARNING !! Experimental approach, requires some manual tweaking
     * Moves each coefficient once into the "right" direction. It always takes the direction with the least
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