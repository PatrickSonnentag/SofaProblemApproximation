import java.util.ArrayList;

/**
 * This class represents all points which COULD be element of the sofa. Points which are not are removed.
 *
 * @author Patrick F. H. Sonnentag
 */
public class R2 {
    private final ArrayList<ArrayList<double[]>> points;
    // These two attributes tell how many points there are in x and y-direction
    private int count_x_points;
    private int count_y_points;
    // Half of the max length of the sofa; range for the x-values and the y-values
    private static final double X_RANGE = 1 + Math.sqrt(2);
    private static final double Y_RANGE = 1;

    /**
     * The constructor of this class. The parameters tell how big the gap between two pints is. A smaller gap means
     * higher accuracy.
     * The x-values of the points are between 0 and 1+sqrt(2), as the maximum length of the sofa is 2+2*sqrt(2) and we
     * only need half of the sofa, as it is symmetric. The y-values are between 0 and 1, as that is the width of the
     * corridor.
     */
    public R2(double delta_x, double delta_y) {
        points = new ArrayList<>();
        count_x_points = (int) (X_RANGE / delta_x);
        count_y_points = (int) (Y_RANGE / delta_y);

        // Depending on which direction has "longer" rows, we choose to structure our points differently
        if (count_x_points > count_y_points) {
            for (double i = 0; i <= Y_RANGE; i += delta_y) {
                // List which contains a singe row (same y-values, different x-values)
                ArrayList<double[]> row = new ArrayList<>();
                for (double j = 0; j <= X_RANGE; j += delta_x) {
                    row.add(new double[]{j, i});
                }
                points.add(row);
            }
        } else {
            for (double i = 0; i <= X_RANGE; i += delta_x) {
                // List which contains a singe row (same x-values, different y-values)
                ArrayList<double[]> column = new ArrayList<>();
                for (double j = 0; j <= Y_RANGE; j += delta_y) {
                    column.add(new double[]{i, j});
                }
                points.add(column);
            }
        }
    }

    public ArrayList<ArrayList<double[]>> getPoints() {
        return points;
    }
}