import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        // Throw error if null argument to constructor
        if (points == null) throw new NullPointerException();

        // Check for null entries
        checkNullEntries(points);

        ArrayList<LineSegment> segmentsList = new ArrayList<LineSegment>();
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);

        // Check for duplicate points on sorted arrays of points
        checkDuplicateEntries(points);

        // Loop through array scanning 4 points at once
        for (int i = 0; i < pointsCopy.length - 3; i++) {
            for (int j = i + 1; j < pointsCopy.length - 2; j++) {
                for (int k = j + 1; k < pointsCopy.length - 1; k++) {
                    if (pointsCopy[i].slopeTo(pointsCopy[j]) != pointsCopy[i]
                            .slopeTo(pointsCopy[k])) {
                        continue;
                    }
                    for (int m = k + 1; m < pointsCopy.length; m++) {
                        if (pointsCopy[i].slopeTo(pointsCopy[j]) == pointsCopy[i]
                                .slopeTo(pointsCopy[k])
                                && pointsCopy[i].slopeTo(pointsCopy[j]) == pointsCopy[i]
                                .slopeTo(pointsCopy[m])) {
                            LineSegment tempLineSegment = new LineSegment(pointsCopy[i],
                                    pointsCopy[m]);
                            if (!segmentsList.contains(tempLineSegment)) {
                                segmentsList.add(tempLineSegment);
                            }
                        }
                    }
                }
            }
        }
        segments = segmentsList.toArray(new LineSegment[segmentsList.size()]);
    }

    private void checkNullEntries(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new NullPointerException();
        }
    }

    private void checkDuplicateEntries(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0)
                throw new IllegalArgumentException();
        }
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return segments.clone();

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
