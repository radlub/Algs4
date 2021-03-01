import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {
        checkException(points);
        ArrayList<LineSegment> storeSegments = new ArrayList<LineSegment>();
        Point[] copyPoints = Arrays.copyOf(points, points.length);
        for (Point point : points) {
            Arrays.sort(copyPoints, point.slopeOrder());
            double tmpSlope = point.slopeTo(copyPoints[0]);
            int count = 1;
            int i;
            for (i = 1; i < copyPoints.length; i++) {
                if (point.slopeTo(copyPoints[i]) == tmpSlope) {
                    count++;

                }
                else {
                    if (count >= 3) {
                        Arrays.sort(copyPoints, i - count, i);
                        if (point.compareTo(copyPoints[i - count]) < 0)
                            storeSegments.add(new LineSegment(point, copyPoints[i - 1]));
                    }
                    tmpSlope = point.slopeTo(copyPoints[i]);
                    count = 1;
                }
            }
            if (count >= 3) {
                Arrays.sort(copyPoints, i - count, i);
                if (point.compareTo(copyPoints[i - count]) < 0)
                    storeSegments.add(new LineSegment(point, copyPoints[i - 1]));
            }
        }
        lineSegments = storeSegments.toArray(new LineSegment[storeSegments.size()]);
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments() {
        return lineSegments.clone();
    }

    private void checkException(Point[] points) {
        if (points == null) throw new NullPointerException();
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i] == null)
                throw new NullPointerException();
            for (int j = i + 1; j < points.length; j++) {
                if (points[j] == null)
                    throw new NullPointerException();
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException();
            }
        }
    }

    public static void main(String[] args) {
        // Read n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // Draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // Print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
