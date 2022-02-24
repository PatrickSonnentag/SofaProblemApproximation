package Curves;

/**
 * @author Patrick F. H. Sonnentag
 */
public class Secant extends Curve {

    public Secant(double[] coefficients) {
        super(coefficients);    // Here there are always only three coefficients
    }

    @Override
    public double get_f_of_x(double x) {
        double f_of_x = (coefficients[0] / Math.cos(coefficients[1] * x)) + coefficients[2];
        return f_of_x;
    }
}