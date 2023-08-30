/* *****************************************************************************
 *  Name: Sohan Kolla
 *  Date: 08/27/2023
 *  Description: A program that examines 4 points at a time and checks whether
 *               they all lie on the same line segment, returning all such line
 *               segments.
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] allSegments;
    private int numSegments = 0;

    public BruteCollinearPoints(Point[] points) { // finds all line segments containing 4 points
        if (points == null) { // making sure the array of points is not null
            throw new IllegalArgumentException();
        }
        if (points.length >= 4) { // if there are 4 or more points, this code will run
            // this check is there because arrays of points of 3 or less can not make line segments
            // of 4 points since not enough points exist.

            Point[] allPoints = new Point[points.length];
            for (int i = 0; i < allPoints.length; i++) {
                // making sure there are no null points
                if (points[i] == null) {
                    throw new IllegalArgumentException();
                }

                // copying over the points to avoid mutating the constructor array.
                allPoints[i] = points[i];
            }

            Arrays.sort(allPoints); // sorting the array so we can check for repeats
            // and also maintain the natural order for when creating line segments

            for (int i = 0; i < allPoints.length - 1; i++) {
                // making sure there are no repeat points
                if (allPoints[i].compareTo(allPoints[i + 1]) == 0) {
                    throw new IllegalArgumentException();
                }
            }

            allSegments = new LineSegment[1]; // using a resizing array to store all line segments

            // brute forcing all possible line segments
            for (int p = 0; p < allPoints.length - 3; p++) {
                for (int q = p + 1; q < allPoints.length - 2; q++) {
                    for (int r = q + 1; r < allPoints.length - 1; r++) {
                        for (int s = r + 1; s < allPoints.length; s++) {
                            double PQ = allPoints[p].slopeTo(allPoints[q]);
                            double PR = allPoints[p].slopeTo(allPoints[r]);
                            double PS = allPoints[p].slopeTo(allPoints[s]);

                            if (PQ == PR && PR == PS) {
                                if (numSegments == allSegments.length) {
                                    resize(allSegments.length * 2);
                                }
                                allSegments[numSegments] = new LineSegment(allPoints[p],
                                                                           allPoints[s]);
                                numSegments++;
                            }
                        }
                    }
                }
            }
        }
        else if (points.length > 1) { // if there is less than 4 points...
            Point[] allPoints = new Point[points.length];
            for (int i = 0; i < allPoints.length; i++) {
                // making sure there are no null points
                if (points[i] == null) {
                    throw new IllegalArgumentException();
                }

                // copying over the points to avoid mutating the constructor array.
                allPoints[i] = points[i];
            }

            Arrays.sort(allPoints); // sorting the array so we can check for repeats
            // and also maintain the natural order for when creating line segments

            for (int i = 0; i < allPoints.length - 1; i++) {
                // making sure there are no repeat points
                if (allPoints[i].compareTo(allPoints[i + 1]) == 0) {
                    throw new IllegalArgumentException();
                }
            }

            allSegments = new LineSegment[0];
            // returning an empty array since no line segments of 4 or more points can even exist.
        }
        else {
            if (points[0] == null) {
                throw new IllegalArgumentException();
            }
            allSegments = new LineSegment[0];
        }
    }

    private void resize(int cap) { // resizing the array
        LineSegment[] copy = new LineSegment[cap];
        for (int i = 0; i < allSegments.length; i++) {
            copy[i] = allSegments[i];
        }
        allSegments = copy;
    }

    public int numberOfSegments() { // the number of line segments
        return numSegments;
    }

    public LineSegment[] segments() { // the line segments
        LineSegment[] copy = new LineSegment[numberOfSegments()];
        for (int i = 0; i < numberOfSegments(); i++) {
            copy[i] = allSegments[i];
        }
        return copy; // defensive copy
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}


