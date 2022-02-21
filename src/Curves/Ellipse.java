package Curves;

/**
 * @author Patrick F. H. Sonnentag
 */
public class Ellipse extends Curve {

    public Ellipse(double[] coefficients) {
        super(coefficients);    // Here there are always only two coefficients
    }

    @Override
    public double get_f_of_x(double x) {
        double f_of_x = (coefficients[1] / coefficients[0]) * Math.sqrt(Math.pow(coefficients[0], 2) - Math.pow(x, 2));
        return f_of_x;
    }
}