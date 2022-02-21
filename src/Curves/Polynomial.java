package Curves;

/**
 * This class is a polynomial of a given degree >1 but only even exponents are used, as we are only interested in
 * functions which are symmetric to the y-axis. It also provides a function to return the function in the for us
 * relevant range.
 *
 * @author Patrick F. H. Sonnentag
 */
public class Polynomial extends Curve {

    private final int degree; // Number of even exponents, so degree == 3 corresponds to the exponents 0, 2 and 4

    public Polynomial(double[] coefficients, int degree) {
        super(coefficients);
        this.degree = degree;
    }

    @Override
    public double get_f_of_x(double x) {
        double f_of_x = 0;
        for (int i = 0; i < degree; i++) {
            f_of_x += coefficients[i] * Math.pow(x, 2 * i);
        }
        return f_of_x;
    }
}