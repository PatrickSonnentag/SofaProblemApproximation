import Curves.Curve;

import java.util.ArrayList;

/**
 * This class constructs a sofa with a given polynomial. It also returns the area which is the wanted measure.
 *
 * @author Patrick F. H. Sonnentag
 */
public class Sofa {
    // All points of the lower and upper envelope of the sofa
    private final ArrayList<ArrayList<double[]>> r2;
    private final Corridor[] corridors;
    // Points which make up the respective Envelopes
    private final ArrayList<double[]> upperEnvelope;
    private final ArrayList<double[]> lowerEnvelope;
    private final double area;

    public Sofa(Curve curve, ArrayList<ArrayList<double[]>> r2, double curve_x_stepSize) {
        ArrayList<double[]> m = curve.getRelevantIntervall(curve_x_stepSize);
        this.r2 = r2;
        corridors = new Corridor[m.size()];
        // Define all corridors with all Points A
        for (int i = 0; i < m.size(); i++) {
            corridors[i] = new Corridor(m.get(i)[0], m.get(i)[1], m.get(i)[2]);
        }

        lowerEnvelope = new ArrayList<>();
        upperEnvelope = new ArrayList<>();
        // Get all values which are all a bit more complicated to find
        getAllEnvelopePoints();
        area = 2 * (calculateEnvelopeArea(upperEnvelope) - calculateEnvelopeArea(lowerEnvelope));
    }

    /**
     * This method finds all points which were found within the sofa by removing points from r2. Because we always
     * search for points which are just not within the sofa, we also immediate determine the envelopes.
     */
    private void getAllEnvelopePoints() {
        // Remove all points if there are no corridors because that means that the curve cannot be used for construction
        if (corridors.length == 0) {
            r2.clear();
        }
        // Check every point in R^2 and determine points of envelopes
        for (ArrayList<double[]> doubles : r2) {
            /*
             * For ech streak of points we search from both ends to the center until we have two defining points
             * for the two envelopes. In the special case there can also be only one. In that case the point is
             * added to both the upper and lower envelope
             */
            boolean isInAllCorridors;    // Must be true after all iterations
            for (double[] aDouble : doubles) {    // Check lower points
                isInAllCorridors = true;
                // Check for every corridor every row of point with every point
                for (Corridor corridor : corridors) {
                    // If lower point is not in every corridor we move on
                    if (!(corridor.isInCorridor(aDouble[0], aDouble[1]))) {
                        isInAllCorridors = false;
                    }
                }
                if (isInAllCorridors) {
                    lowerEnvelope.add(new double[]{aDouble[0], aDouble[1]});
                    break;
                }
            }
            for (int j = doubles.size() - 1; j >= 0; j--) {    // Check lower points
                isInAllCorridors = true;
                // Check for every corridor every row of point with every point
                for (Corridor corridor : corridors) {
                    // If lower point is not in every corridor we move on
                    if (!(corridor.isInCorridor(doubles.get(j)[0], doubles.get(j)[1]))) {
                        isInAllCorridors = false;
                    }
                }
                if (isInAllCorridors) {
                    upperEnvelope.add(new double[]{doubles.get(j)[0], doubles.get(j)[1]});
                    break;
                }
            }
        }
        // Add this point as it is most critical for the area but is removed because of floating point precision
        upperEnvelope.add(new double[]{0, 1});
    }

    /**
     * This function takes both envelopes, calculates their area by approximating it through trapezoids. Then it can
     * subtract these areas which reulst in the area of the sofa.
     */
    private double calculateEnvelopeArea(ArrayList<double[]> envelope) {
        double area = 0;
        for (int i = 0; i < envelope.size() - 1; i++) {
            if (envelope.get(i)[0] == envelope.get(i + 1)[0]) {
                // Do nothing as area doesn't change
            } else {
                double a = envelope.get(i)[1];
                double c = envelope.get(i + 1)[1];
                double h = Math.abs(envelope.get(i + 1)[0] - envelope.get(i)[0]);
                area += (a + c) / 2 * h;
            }
        }
        return area;
    }

    public double getArea() {
        return area;
    }
}