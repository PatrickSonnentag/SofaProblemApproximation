package Curves;

/**
 * @author Patrick F. H. Sonnentag
 */
public class HyperbolicCosine extends Curve {
    public HyperbolicCosine(double[] coefficients) {
        super(coefficients);    // Here there are always only four coefficients
    }

    @Override
    public double get_f_of_x(double x) {
        double f_of_x = coefficients[0] * (Math.exp(coefficients[2] * x) + Math.exp(-coefficients[2] * x)) /
                (coefficients[1]) + coefficients[3];
        return f_of_x;
    }
}