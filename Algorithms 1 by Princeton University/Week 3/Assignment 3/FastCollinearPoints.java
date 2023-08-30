/* *****************************************************************************
 *  Name: Sohan Kolla
 *  Date: 08/27/2023
 *  Description: Given a point p, the following method determines whether p
 *               participates in a set of 4 or more collinear points.
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;

public class FastCollinearPoints {
    private LinkedList<LineSegment> allSegments;

    public FastCollinearPoints(Point[] points) { // finds all line segments containing 4+ points
        if (points == null) { // making sure the array is not null
            throw new IllegalArgumentException();
        }

        Point[] sortedPoints = new Point[points.length]; // creating an array to store the points

        for (int i = 0; i < points.length; i++) { // cycling through the input array
            if (points[i] == null) { // making sure there are no null points in the input array
                throw new IllegalArgumentException(); // returns error if there is a null point
            }

            // copying over the points to avoid mutating the constructor array.
            sortedPoints[i] = points[i];
        }

        Arrays.sort(sortedPoints); // sorting the array so we can check for repeats
        // and also to maintain the natural order to refer back to later

        if (sortedPoints.length > 1) {
            // the array only needs to be checked for repeats if there is more than one point
            for (int i = 0; i < sortedPoints.length - 1; i++) {
                if (sortedPoints[i].compareTo(sortedPoints[i + 1]) == 0) { // checking for repeats
                    throw new IllegalArgumentException(); // returns error if it does have a repeat
                }
            }
        }

        allSegments = new LinkedList<LineSegment>();
        // creating a linked list to store all the line segments.

        for (int i = 0; i < sortedPoints.length; i++) {
            // this for loop runs through all the points, sorted by natural order.

            Point p = sortedPoints[i];
            // for each and every iteration of the for loop, a new point is considered as the "P"
            // point, we will consider this "P" point as the origin.
            // allows all points in the array to be considered as point "P" at some time because of
            // the for-loop.

            Point[] sortedBySlope = sortedPoints.clone();
            Arrays.sort(sortedBySlope, p.slopeOrder());
            // sorts the array based on slopes from the point P.
            // for multiple points that have the same slope from point P, it will keep them in
            // natural order dictated by the sort called above.
            // We can do this because Arrays.sort() is stable so when we re-sort it with a different
            // sort pattern, it will keep elements with the same value in the order they were in
            // prior to getting re-sorted.

            int q = 1;
            // now that we have a point P as our "origin" and have sorted the other points based on
            // their slope from P, we can iterate through the sortedBySlope array using "q" as our
            // index.

            while (q < sortedBySlope.length) { // while our index is in the array...
                LinkedList<Point> possibleSegment = new LinkedList<>();
                // creates a new linked list that stores points that can possibly contribute to a
                // new line segment (of 4+ collinear points).
                // This linked list only gets created when two adjacent points are not collinear,
                // when adjacent points are collinear, they will instead get added to an existing
                // linked list.

                double currentSlope = p.slopeTo(sortedBySlope[q]);
                // takes the slope from p to the point at the current index to check adjacent points
                // for collinearity (same slope from p).

                while (q < sortedBySlope.length && p.slopeTo(sortedBySlope[q]) == currentSlope) {
                    // while our current index is in the array and the slope from p to the point at
                    // the current index is the same as the current slope we are checking
                    // collinearity for (i.e. current point is collinear with current slope we are
                    // checking)...

                    possibleSegment.add(sortedBySlope[q]); // add the point to the linked list
                    q++; // increment our index tracker
                }

                if (possibleSegment.size() >= 3 && p.compareTo(possibleSegment.getFirst()) < 0) {
                    // if the possible segment has at least 3 points, when added with point P you
                    // have a line segment of at least 4 collinear points.
                    // we also check that p is the smallest point in the line segment to avoid
                    // adding duplicate line segments to our linked list.
                    // if each point P takes care of all the line segments that start with that
                    // point P, then no duplicates will be added.

                    allSegments.add(new LineSegment(p, possibleSegment.getLast()));
                    // adding the line segment to the line segment linked list with point P as the
                    // beginning and the last value of our possibleSegment linked list as the end.
                }
            }
        }
        // The inner while loop checks consecutive points for collinearity and adds them to a
        // linked list of points that can possibly become a line segment (4+ collinear points).
        // The inner while loop terminates when the latest consecutive point is not collinear with
        // the previous points. Once the inner while loop terminates, we check if the current linked
        // list is big enough to become a line segment.
        // If it is not big enough, we finish the iteration of the outer while loop and create a new
        // linked list and check new points with a new slope.
        // If it is big enough, IT MUST ALSO BE TRUE THAT THE CURRENT POINT P IS THE SMALLEST IN THE
        // LINE SEGMENT. By enforcing that the current P must be the smallest in all the line
        // segments found through ordering by slope from the current P, we avoid duplicates as each
        // new point P would only add line segments where that P is the smallest.
        // Finally, the for-loop runs through all the points in the array, allowing for each and
        // every point to eventually be considered as point P at some time. This allows all possible
        // line segments to be eventually added to our linked list of all the segments over the
        // course of our for loop.
    }

    public int numberOfSegments() { // the number of line segments
        return allSegments.size(); // size of the linked list containing all the line segments
    }

    public LineSegment[] segments() { // the line segments
        // creating and returning a defensive copy for data integrity
        LineSegment[] copy = new LineSegment[allSegments.size()];
        return allSegments.toArray(copy);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
